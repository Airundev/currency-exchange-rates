package com.example.currencyexchangerates.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.ui.components.CurrencyListCellAdapter
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import com.example.currencyexchangerates.ui.main.utils.LifecycleAwareTimer
import com.example.currencyexchangerates.ui.main.utils.LiveDataResult
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class CurrencyRateActivity : AppCompatActivity(), CurrencyListCellAdapter.CurrencyListCellListener {

    private val currencyRateViewModel: CurrencyRateViewModel by viewModel()

    private lateinit var adapter: CurrencyListCellAdapter

    private val timer: LifecycleAwareTimer by lazy {
        LifecycleAwareTimer(::onTimerTick)
    }

    private val resultObserver = Observer<LiveDataResult<MutableList<CurrencyListCellItem>>> { result ->
        when (result.status) {
            LiveDataResult.Status.SUCCESS -> {
                swipeLayout.isRefreshing = false
                result.data?.let { adapter.updateItems(it) }
                lifecycle.addObserver(timer)
            }
            LiveDataResult.Status.LOADING -> {
                swipeLayout.isRefreshing = true
            }
            LiveDataResult.Status.ERROR -> {
                swipeLayout.isRefreshing = false
                timer.onPause()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        adapter = CurrencyListCellAdapter(this)
        adapter.setHasStableIds(true)
        swipeLayout.setOnRefreshListener { timer.onResume() }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        currencyRateViewModel.liveDataResult.observe(this, resultObserver)
        currencyRateViewModel.start()
    }

    override fun onCurrencyCellClicked(baseCurrency: String, baseValue: String) {
        currencyRateViewModel.baseCurrency = baseCurrency
        currencyRateViewModel.baseValue = baseValue
        recyclerView.layoutManager?.scrollToPosition(0)
    }

    override fun onBaseValueUpdated(baseValue: String) {
        currencyRateViewModel.baseValue = baseValue
        currencyRateViewModel.updateValues(adapter.items)
    }

    private fun onTimerTick() {
        currencyRateViewModel.updateList(adapter.items)
    }
}