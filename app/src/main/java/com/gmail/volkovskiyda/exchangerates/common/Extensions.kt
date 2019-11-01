package com.gmail.volkovskiyda.exchangerates.common

import java.text.SimpleDateFormat
import java.util.*

private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)

fun Date.format(): String = dateFormat.format(this)
fun String.format(): Date = dateFormat.parse(this)!!

fun Date.toCalendar(): Calendar = Calendar.getInstance().apply { time = this@toCalendar }
fun Date.add(field: Int, amount: Int): Date = toCalendar().apply { add(field, amount) }.time