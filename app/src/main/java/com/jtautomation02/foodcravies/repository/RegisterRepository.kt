package com.jtautomation02.foodcravies.repository

import com.jtautomation02.foodcravies.model.RegisterUserRequest
import com.jtautomation02.foodcravies.model.RegisterUserResponse
import com.jtautomation02.foodcravies.remote.FoodCraveApiService
import com.jtautomation02.foodcravies.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterRepository @Inject constructor(
    private val apiService: FoodCraveApiService
) {
    suspend fun register(registerRequest: RegisterUserRequest): Result<RegisterUserResponse>
    {
        return withContext(Dispatchers.IO) {
            try {
                val responseSuccess: String = "True"
                val response = apiService.register(registerRequest)
                if (response.isSuccessful && response.body() != null) {
                    val registerUserResponse = response.body()!!
                    if(registerUserResponse.success == responseSuccess && registerUserResponse.userId.isNotEmpty()){
                        Result.Success(registerUserResponse)
                    }
                    else{
                        Result.Error<RegisterUserResponse>(registerUserResponse.errorMessage)
                    }
                } else {
                    Result.Error("Server error: ${response.code()}")
                }
            }
            catch (e: Exception) {
                Result.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }
}