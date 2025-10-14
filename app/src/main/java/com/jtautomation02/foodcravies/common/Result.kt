package com.jtautomation02.foodcravies.common

sealed class Result<out T> {
    data object Idle : Result<Nothing>()
    data object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<T>(val message: String) : Result<T>()
}
