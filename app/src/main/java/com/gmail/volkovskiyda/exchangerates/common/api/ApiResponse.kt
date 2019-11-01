package com.gmail.volkovskiyda.exchangerates.common.api

sealed class ApiResponse<T>(open val data: T?) {
    data class Success<T>(override val data: T) : ApiResponse<T>(data)
    data class Error<T>(val throwable: Throwable, val error: String) : ApiResponse<T>(data = null) {
        constructor(apiException: ApiException) : this(apiException.throwable, apiException.error)
    }
}