package com.jtautomation02.foodcravies.remote

import com.jtautomation02.foodcravies.model.GoogleSignInAccount
import com.jtautomation02.foodcravies.model.LoginUserRequest
import com.jtautomation02.foodcravies.model.LoginUserResponse
import com.jtautomation02.foodcravies.model.RegisterUserRequest
import com.jtautomation02.foodcravies.model.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FoodCraveApiService {

    @POST("auth/login")
    suspend fun login(@Body loginUserRequest: LoginUserRequest): Response<LoginUserResponse>
    @POST("user/register")
    suspend fun register(@Body registerUserRequest: RegisterUserRequest): Response<RegisterUserResponse>

    @POST("auth/googlesignin")
    suspend fun loginwithgoogle(@Body googleSignInAccount: GoogleSignInAccount): Response<LoginUserResponse>

}