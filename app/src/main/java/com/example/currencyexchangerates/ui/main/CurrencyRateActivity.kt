package com.example.currencyexchangerates.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.ui.components.CurrencyListCellAdapter
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import com.example.currencyexchangerates.ui.main.utils.LiveDataResult
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class CurrencyRateActivity : AppCompatActivity(), CurrencyListCellAdapter.CurrencyListCellListener {

    private val currencyRateViewModel: CurrencyRateViewModel by viewModel()

    private lateinit var adapter: CurrencyListCellAdapter

    private val resultObserver = Observer<LiveDataResult<List<CurrencyListCellItem>>> { result ->
        when (result.status) {
            LiveDataResult.Status.SUCCESS -> {
                //TODO: Implement progressDialog and hide it here
                result.data?.let { adapter.setItems(it) }
            }
            LiveDataResult.Status.LOADING -> {
                //TODO: Implement progressDialog and show it here
            }
            LiveDataResult.Status.ERROR -> {
                //TODO: Implement progressDialog and hide it here
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        adapter = CurrencyListCellAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        currencyRateViewModel.liveDataResult.observe(this, resultObserver)
        currencyRateViewModel.updateList()
    }

    override fun onCurrencyCellClicked(item: CurrencyListCellItem) {
        adapter.onCellClicked(item)
    }
}