package com.gmail.volkovskiyda.exchangerates.common.db

import androidx.paging.DataSource
import androidx.room.*
import java.util.*

@Dao
interface MainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ExchangeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<ExchangeRateEntity>)

    @Transaction
    @Query("SELECT * FROM exchange WHERE date <= :fromDate ORDER BY date DESC")
    fun exchangeRates(fromDate: Date): DataSource.Factory<Int, Exchange>
}