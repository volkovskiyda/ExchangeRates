package com.gmail.volkovskiyda.exchangerates.main

import androidx.paging.PagedList
import androidx.room.withTransaction
import com.gmail.volkovskiyda.exchangerates.common.Exchange
import com.gmail.volkovskiyda.exchangerates.common.add
import com.gmail.volkovskiyda.exchangerates.common.api.ApiService
import com.gmail.volkovskiyda.exchangerates.common.api.dto.ExchangeResponse
import com.gmail.volkovskiyda.exchangerates.common.db.ExchangeEntity
import com.gmail.volkovskiyda.exchangerates.common.db.ExchangeRateEntity
import com.gmail.volkovskiyda.exchangerates.common.db.MainDatabase
import com.gmail.volkovskiyda.exchangerates.common.format
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class MainBoundaryCallback(
    private val api: ApiService,
    private val database: MainDatabase,
    private val scope: CoroutineScope
) : PagedList.BoundaryCallback<Exchange>() {

    override fun onZeroItemsLoaded() {
        exchangeRates(Date())
    }

    override fun onItemAtEndLoaded(itemAtEnd: Exchange) {
        exchangeRates(itemAtEnd.date.add(field = Calendar.DATE, amount = -1))
    }

    override fun onItemAtFrontLoaded(itemAtFront: Exchange) {
        val newDate = itemAtFront.date.add(field = Calendar.DATE, amount = +1)
        if (newDate.before(Date())) exchangeRates(newDate)
    }

    private fun exchangeRates(date: Date) =
        scope.launch {
            val apiResponse = api.exchangeRates(date.format())
            apiResponse.data?.run {
                database.withTransaction {
                    with(database.mainDao()) {
                        insert(mapToEntity())
                        insert(mapToEntities())
                    }
                }
            }
        }

    private fun ExchangeResponse.mapToEntity(): ExchangeEntity =
        ExchangeEntity(
            date = date.format(),
            bank = bank
        )

    private fun ExchangeResponse.mapToEntities(): List<ExchangeRateEntity> =
        exchangeRate
            .filterNot { rate -> rate.currency.isNullOrEmpty() }
            .map { rate ->
                ExchangeRateEntity(
                    date = date.format(),
                    currency = rate.currency.orEmpty(),
                    baseCurrency = rate.baseCurrency,
                    saleRateNB = rate.saleRateNB,
                    purchaseRateNB = rate.purchaseRateNB,
                    saleRate = rate.saleRate,
                    purchaseRate = rate.purchaseRate
                )
            }
}