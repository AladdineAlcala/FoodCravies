package com.jtautomation02.foodcravies.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class LoginUserResponse(
    @Json(name = "success")
    val success: Boolean,

    @Json(name = "accessToken")
    val accessToken: String,

    @Json(name = "accessTokenExpires")
    val accessTokenExpires: String,

    @Json(name = "message")
    val message: String,

    @Json(name = "refreshTokenResponse")
    val refreshTokenResponse: RefreshTokenResponse,

    @Json(name = "errorTitle")
    val errorTitle: String?,

    @Json(name = "errorMessage")
    val errorMessage: String?,

    @Json(name = "errorCode")
    val errorCode: String?
)
@JsonClass(generateAdapter = true)
data class RefreshTokenResponse(

    @Json(name = "id")
    val id: Int,

    @Json(name = "token")
    val token: String?,

    @Json(name = "userId")
    val userId: String?,

    @Json(name = "expires")
    val expires: String,

    @Json(name = "created")
    val created: String,

    @Json(name = "revoked")
    val revoked: String?,

    @Json(name = "isRememberMe")
    val isRememberMe: String, // Or Boolean, if API can be changed

    @Json(name = "deviceInfo")
    val deviceInfo: String?, // Assuming it's a string, can be another data class if complex

    @Json(name = "ipAddress")
    val ipAddress: String?
)