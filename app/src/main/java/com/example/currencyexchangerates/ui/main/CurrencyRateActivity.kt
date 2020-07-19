package com.example.currencyexchangerates.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currencyexchangerates.R
import org.koin.android.viewmodel.ext.android.viewModel

class CurrencyRateActivity : AppCompatActivity() {

    private val currencyRateViewModel: CurrencyRateViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}