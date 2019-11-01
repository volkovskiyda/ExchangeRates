package com.gmail.volkovskiyda.exchangerates.common.db

import androidx.room.Entity
import java.util.*

@Entity(tableName = "exchange_rate", primaryKeys = ["date", "currency"])
data class ExchangeRateEntity(
    val date: Date,
    val currency: String,
    val baseCurrency: String,
    val saleRateNB: Double,
    val purchaseRateNB: Double,
    val saleRate: Double?,
    val purchaseRate: Double?
)