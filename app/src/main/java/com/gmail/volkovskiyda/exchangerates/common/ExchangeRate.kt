package com.gmail.volkovskiyda.exchangerates.common

import java.util.*

data class ExchangeRate(
    val date: Date,
    val currency: String,
    val baseCurrency: String,
    val saleRateNB: Double,
    val purchaseRateNB: Double,
    val saleRate: Double?,
    val purchaseRate: Double?
)