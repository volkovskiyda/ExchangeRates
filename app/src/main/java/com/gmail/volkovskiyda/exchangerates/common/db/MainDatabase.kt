package com.gmail.volkovskiyda.exchangerates.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [ExchangeEntity::class, ExchangeRateEntity::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao
}