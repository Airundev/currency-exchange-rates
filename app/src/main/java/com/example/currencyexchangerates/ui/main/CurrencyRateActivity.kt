package com.example.currencyexchangerates.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.ui.components.CurrencyListCellAdapter
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class CurrencyRateActivity : AppCompatActivity(), CurrencyListCellAdapter.CurrencyListCellListener {

    private val currencyRateViewModel: CurrencyRateViewModel by viewModel()

    private lateinit var adapter: CurrencyListCellAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        adapter = CurrencyListCellAdapter()
        recyclerView.adapter = adapter
        adapter.listener = this
    }

    override fun onCurrencyCellClicked(item: CurrencyListCellItem) {
        adapter.onCellClicked(item)
    }
}