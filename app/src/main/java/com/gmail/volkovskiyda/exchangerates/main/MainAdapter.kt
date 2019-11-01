package com.gmail.volkovskiyda.exchangerates.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.volkovskiyda.exchangerates.R
import com.gmail.volkovskiyda.exchangerates.common.Exchange
import com.gmail.volkovskiyda.exchangerates.common.format
import kotlinx.android.synthetic.main.item_exchange_rate.view.*

class MainAdapter : PagedListAdapter<Exchange, MainAdapter.ExchangeRateVH>(diiCallback) {
    companion object {
        val diiCallback = object : DiffUtil.ItemCallback<Exchange>() {
            override fun areItemsTheSame(oldItem: Exchange, newItem: Exchange): Boolean =
                oldItem.date == newItem.date

            override fun areContentsTheSame(oldItem: Exchange, newItem: Exchange): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateVH =
        ExchangeRateVH(parent)

    override fun onBindViewHolder(holder: ExchangeRateVH, position: Int) {
        val exchange = getItem(position)
        with(holder.itemView) {
            date.text = exchange?.date?.format()
            rate.text = exchange?.exchangeRate
                ?.filter { it.currency in arrayOf("USD", "EUR") }
                ?.joinToString(
                    separator = "\n",
                    transform = { "${it.currency}\t\t${it.purchaseRate}\t\t${it.saleRate}" }
                )
        }
    }

    inner class ExchangeRateVH(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_exchange_rate,
            parent,
            false
        )
    )
}