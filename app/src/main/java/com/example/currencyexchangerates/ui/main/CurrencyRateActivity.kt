package com.example.currencyexchangerates.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currencyexchangerates.R

class CurrencyRateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CurrencyRateFragment.newInstance())
                    .commitNow()
        }
    }
}