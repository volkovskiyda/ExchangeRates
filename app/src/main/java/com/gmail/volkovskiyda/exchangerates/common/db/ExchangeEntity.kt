package com.gmail.volkovskiyda.exchangerates.common.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "exchange")
data class ExchangeEntity(
    @PrimaryKey val date: Date,
    val bank: String
)