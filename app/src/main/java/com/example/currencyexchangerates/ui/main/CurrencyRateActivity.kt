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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class CurrencyRateActivity : AppCompatActivity() {

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
            }
            LiveDataResult.Status.LOADING -> {
                swipeLayout.isRefreshing = true
            }
            LiveDataResult.Status.ERROR -> {
                swipeLayout.isRefreshing = false
                timer.onPause()
                Snackbar.make(swipeLayout, getString(R.string.errorText), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        adapter = CurrencyListCellAdapter(::onCurrencyCellClicked, currencyRateViewModel::updateValues)
        adapter.setHasStableIds(true)
        swipeLayout.setOnRefreshListener { if (timer.canRefresh) timer.onResume() }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        currencyRateViewModel.liveDataResult.observe(this, resultObserver)
        lifecycle.addObserver(timer)
    }

    private fun onCurrencyCellClicked(baseCurrency: String, baseValue: String, currentList: MutableList<CurrencyListCellItem>) {
        currencyRateViewModel.updateBaseData(baseCurrency, baseValue, currentList)
        recyclerView.layoutManager?.scrollToPosition(0)
    }

    private fun onTimerTick() {
        if (timer.canTick) currencyRateViewModel.updateList()
    }
}