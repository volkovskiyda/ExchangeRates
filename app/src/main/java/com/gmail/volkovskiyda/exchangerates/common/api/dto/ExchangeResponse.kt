package com.gmail.volkovskiyda.exchangerates.common.api.dto

import com.google.gson.annotations.SerializedName

data class ExchangeResponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("bank")
    val bank: String,
    @SerializedName("baseCurrency")
    val baseCurrency: Int,
    @SerializedName("baseCurrencyLit")
    val baseCurrencyLit: String,
    @SerializedName("exchangeRate")
    val exchangeRate: List<ExchangeRateResponse>
)