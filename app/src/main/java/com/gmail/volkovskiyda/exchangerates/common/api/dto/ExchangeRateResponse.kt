package com.gmail.volkovskiyda.exchangerates.common.api.dto

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("baseCurrency")
    val baseCurrency: String,
    @SerializedName("saleRateNB")
    val saleRateNB: Double,
    @SerializedName("purchaseRateNB")
    val purchaseRateNB: Double,
    @SerializedName("saleRate")
    val saleRate: Double?,
    @SerializedName("purchaseRate")
    val purchaseRate: Double?
)