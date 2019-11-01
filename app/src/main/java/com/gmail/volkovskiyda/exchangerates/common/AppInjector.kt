package com.gmail.volkovskiyda.exchangerates.common

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.gmail.volkovskiyda.exchangerates.MainApp
import com.gmail.volkovskiyda.exchangerates.di.DaggerAppComponent
import com.gmail.volkovskiyda.exchangerates.di.Injectable
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

object AppInjector {
    fun init(app: MainApp) {
        DaggerAppComponent.builder().application(application = app).build().inject(app = app)
        app.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksDelegate() {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                if (isInjectable(activity)) {
                    AndroidInjection.inject(activity)
                }
                if (activity is FragmentActivity) {
                    activity.supportFragmentManager.registerFragmentLifecycleCallbacks(object :
                        FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            fragment: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (isInjectable(
                                    fragment
                                )
                            ) {
                                AndroidSupportInjection.inject(fragment)
                            }
                        }
                    }, true)
                }
            }
        })
    }

    private fun isInjectable(any: Any?): Boolean = any is Injectable || any is HasAndroidInjector
}