package com.jtautomation02.foodcravies.model

import com.squareup.moshi.Json

open class CommonResponse(
    @Json(name = "errorTitle")
    open val errorTitle: String = "",

    @Json(name = "errorMessage")
    open val errorMessage: String = "",

    @Json(name = "errorCode")
    open val  errorCode: String = ""
)