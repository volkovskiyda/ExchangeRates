package com.gmail.volkovskiyda.exchangerates.common.api

import com.gmail.volkovskiyda.exchangerates.common.api.dto.ExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("exchange_rates?json")
    suspend fun exchangeRates(@Query("date") date: String? = null): ApiResponse<ExchangeResponse>
}