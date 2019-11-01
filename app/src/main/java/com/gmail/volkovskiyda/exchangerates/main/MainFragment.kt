package com.gmail.volkovskiyda.exchangerates.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gmail.volkovskiyda.exchangerates.R
import com.gmail.volkovskiyda.exchangerates.di.Injectable
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main), Injectable {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainAdapter()
        rvExchangeRates.adapter = adapter
        viewModel.exchangeRates.observe(viewLifecycleOwner, Observer { exchangeRates ->
            adapter.submitList(exchangeRates) {
                rvExchangeRates.smoothScrollToPosition(adapter.itemCount)
            }
        })
    }
}