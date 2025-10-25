package com.jtautomation02.foodcravies.repository

import com.jtautomation02.foodcravies.common.Result
import com.jtautomation02.foodcravies.model.GoogleSignInAccount
import com.jtautomation02.foodcravies.model.LoginUserRequest
import com.jtautomation02.foodcravies.model.LoginUserResponse
import com.jtautomation02.foodcravies.remote.FoodCraveApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import com.squareup.moshi.Moshi
import java.io.IOException

@Singleton
class LoginRepository @Inject constructor(
    private val apiService: FoodCraveApiService,
    private val moshi: Moshi
) {
    suspend fun login(loginUserRequest: LoginUserRequest) : Result<LoginUserResponse>
    {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.login(loginUserRequest)
                if(result.isSuccessful && result.body() != null){
                    val loginUserResponse: LoginUserResponse = result.body()!!
                    if(loginUserResponse.success && loginUserResponse.accessToken.isNotEmpty()){
                        Result.Success(loginUserResponse)
                    }
                    else{
                        Result.Error<LoginUserResponse>(loginUserResponse?.errorMessage ?: "An unexpected error occurred")
                    }
                }
                else{
                    val statusCode = result.code()
                    val errorBodyString = result.errorBody()?.string()
                    val parsedError = parseErrorBody(errorBodyString)
                    val errorMessage = when (statusCode) {
                        401 -> "Unauthorized: Invalid email or password. ${parsedError.message}"
                        else -> "Server error ($statusCode): ${parsedError.message} (Code: ${parsedError.code})"
                    }
                    Result.Error(errorMessage.trim())
                }

            } catch (e: IOException) {
                Result.Error("Network error: Please check your connection.")
            } catch (e: Exception) {
                Result.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

    suspend fun loginWithGoogle(googleSignInAccount: GoogleSignInAccount): Result<LoginUserResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val result=apiService.loginwithgoogle(googleSignInAccount)
                if(result.isSuccessful && result.body() != null){
                    val loginUserResponse: LoginUserResponse = result.body()!!
                    if(loginUserResponse.success && loginUserResponse.accessToken.isNotEmpty()){
                        Result.Success(loginUserResponse)
                    }
                    else{
                        Result.Error<LoginUserResponse>(loginUserResponse?.errorMessage ?: "An unexpected error occurred")
                    }
                }
               else{
                    Result.Error("Network error: Please check your connection.")
               }
            } catch (e: IOException) {
                Result.Error("Network error: Please check your connection.")
            } catch (e: Exception) {
                Result.Error("An unexpected error occurred: ${e.message}")
            }
        }

    }


    /**
     * Helper function to parse the error JSON from the response body.
     */
    private fun parseErrorBody(errorBody: String?): ParsedError {
        if (errorBody.isNullOrEmpty()) {
            return ParsedError("Could not get error details from server.", "")
        }
        return try {
            val errorAdapter = moshi.adapter(LoginUserResponse::class.java)
            val errorResponse = errorAdapter.fromJson(errorBody)
            val message = errorResponse?.errorMessage?.takeIf { it.isNotBlank() } ?: "No error message provided."
            val code = errorResponse?.errorCode?.takeIf { it.isNotBlank() } ?: ""
            ParsedError(message, code)
        } catch (e: Exception) {
            ParsedError("Error parsing server response.", "")
        }
    }

    /**
     * A small helper data class to hold parsed error details.
     */
    private data class ParsedError(val message: String, val code: String)
}