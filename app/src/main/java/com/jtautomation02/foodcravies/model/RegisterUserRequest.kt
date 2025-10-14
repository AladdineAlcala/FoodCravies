package com.jtautomation02.foodcravies.model

data class RegisterUserRequest(
    val email: String,
    val password: String,
    val username: String,
)
