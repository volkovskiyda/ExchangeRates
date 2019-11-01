package com.gmail.volkovskiyda.exchangerates.main

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.gmail.volkovskiyda.exchangerates.common.Exchange
import com.gmail.volkovskiyda.exchangerates.common.ExchangeRate
import com.gmail.volkovskiyda.exchangerates.common.api.ApiService
import com.gmail.volkovskiyda.exchangerates.common.db.ExchangeRateEntity
import com.gmail.volkovskiyda.exchangerates.common.db.MainDatabase
import kotlinx.coroutines.CoroutineScope
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import com.gmail.volkovskiyda.exchangerates.common.db.Exchange as ExchangeEntity

@Singleton
class MainRepository
@Inject constructor(
    private val api: ApiService,
    private val database: MainDatabase
) {

    private lateinit var scope: CoroutineScope

    fun setScope(scope: CoroutineScope) {
        this.scope = scope
    }

    fun exchanges(from: Date): LiveData<PagedList<Exchange>> =
        database.mainDao().exchangeRates(from)
            .map(::mapEntity)
            .toLiveData(
                boundaryCallback = MainBoundaryCallback(api, database, scope),
                pageSize = 30
//                config = PagedList.Config.Builder()
//                    .setPageSize(30)
//                    .setPrefetchDistance(10)
//                    .setMaxSize(100)
//                    .setEnablePlaceholders(true)
//                    .build()
            )

    private fun mapEntity(entity: ExchangeEntity): Exchange = Exchange(
        date = entity.exchange.date,
        exchangeRate = entity.exchangeRate.map(::mapEntity)
    )

    private fun mapEntity(entity: ExchangeRateEntity): ExchangeRate = ExchangeRate(
        date = entity.date,
        currency = entity.currency,
        baseCurrency = entity.baseCurrency,
        saleRateNB = entity.saleRateNB,
        purchaseRateNB = entity.purchaseRateNB,
        saleRate = entity.saleRate,
        purchaseRate = entity.purchaseRate
    )
}