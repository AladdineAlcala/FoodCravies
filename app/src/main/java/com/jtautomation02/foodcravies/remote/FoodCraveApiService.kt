package com.jtautomation02.foodcravies.remote

import com.jtautomation02.foodcravies.model.RegisterUserRequest
import com.jtautomation02.foodcravies.model.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

public  interface FoodCraveApiService {
    @POST("user/register")
    suspend fun register(@Body registerUserRequest: RegisterUserRequest): Response<RegisterUserResponse>
}