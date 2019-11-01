package com.gmail.volkovskiyda.exchangerates.common

import android.content.Context
import android.content.ContextWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider
@Inject constructor(
    context: Context
) : ContextWrapper(context.applicationContext)