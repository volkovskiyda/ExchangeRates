package com.gmail.volkovskiyda.exchangerates

import android.app.Application
import com.gmail.volkovskiyda.exchangerates.common.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class MainApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        val timberTree = if (BuildConfig.DEBUG) Timber.DebugTree() else ReleaseTree()
        Timber.plant(timberTree)
        AppInjector.init(this)
    }

    class ReleaseTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
            //NO-OP
        }
    }
}