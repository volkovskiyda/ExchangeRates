package com.gmail.volkovskiyda.exchangerates.common.db

import androidx.room.Embedded
import androidx.room.Relation

data class Exchange(
    @Embedded val exchange: ExchangeEntity,
    @Relation(
        parentColumn = "date",
        entityColumn = "date"
    )
    val exchangeRate: List<ExchangeRateEntity>
)