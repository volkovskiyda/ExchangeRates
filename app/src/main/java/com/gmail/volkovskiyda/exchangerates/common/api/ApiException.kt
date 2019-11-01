package com.gmail.volkovskiyda.exchangerates.common.api

data class ApiException(val throwable: Throwable, val error: String) : RuntimeException()