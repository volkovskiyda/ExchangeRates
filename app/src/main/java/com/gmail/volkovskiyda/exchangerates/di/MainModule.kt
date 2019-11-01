package com.gmail.volkovskiyda.exchangerates.di

import androidx.lifecycle.ViewModel
import com.gmail.volkovskiyda.exchangerates.main.MainFragment
import com.gmail.volkovskiyda.exchangerates.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface MainModule {

    @ContributesAndroidInjector
    fun contributeMainFragment(): MainFragment

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}