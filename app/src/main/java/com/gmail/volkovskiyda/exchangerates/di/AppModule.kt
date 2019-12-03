package com.gmail.volkovskiyda.exchangerates.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.gmail.volkovskiyda.exchangerates.BuildConfig
import com.gmail.volkovskiyda.exchangerates.common.api.ApiResponseCallAdapterFactory
import com.gmail.volkovskiyda.exchangerates.common.api.ApiService
import com.gmail.volkovskiyda.exchangerates.common.db.MainDao
import com.gmail.volkovskiyda.exchangerates.common.db.MainDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
            })
            .build()

    @Singleton
    @Provides
    fun provideApiService(
        okHttpClient: OkHttpClient,
        callAdapterFactory: ApiResponseCallAdapterFactory
    ): ApiService =
        Retrofit.Builder()
            .baseUrl("https://api.privatbank.ua/p24api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(callAdapterFactory)
            .client(okHttpClient)
            .build()
            .create()

    @Singleton
    @Provides
    fun provideDatabase(context: Context): MainDatabase =
        Room.databaseBuilder(context, MainDatabase::class.java, "exchangerates.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDao(database: MainDatabase): MainDao = database.mainDao()
}