package com.gmail.volkovskiyda.exchangerates.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.gmail.volkovskiyda.exchangerates.common.Exchange
import java.util.*
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {

    init {
        mainRepository.setScope(viewModelScope)
    }

    val exchangeRates: LiveData<PagedList<Exchange>> = mainRepository.exchanges(Date())
}