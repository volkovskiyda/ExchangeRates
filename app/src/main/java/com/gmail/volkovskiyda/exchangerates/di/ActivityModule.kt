package com.gmail.volkovskiyda.exchangerates.di

import com.gmail.volkovskiyda.exchangerates.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    fun contributeMainActivity(): MainActivity
}