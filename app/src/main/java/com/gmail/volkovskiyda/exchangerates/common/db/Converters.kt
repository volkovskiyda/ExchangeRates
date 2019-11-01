package com.gmail.volkovskiyda.exchangerates.common.db

import androidx.room.TypeConverter
import java.util.*

open class Converters {
    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}