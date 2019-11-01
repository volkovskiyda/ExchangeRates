package com.gmail.volkovskiyda.exchangerates.common

import java.util.*

data class Exchange(
    val date: Date,
    val exchangeRate: List<ExchangeRate>
)