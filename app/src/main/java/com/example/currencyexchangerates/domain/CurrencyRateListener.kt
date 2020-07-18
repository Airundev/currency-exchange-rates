package com.example.currencyexchangerates.domain

import com.example.currencyexchangerates.data.model.CurrencyRatesModel

interface CurrencyRateListener {
    fun onSuccess(ratesModel: CurrencyRatesModel)
    fun onError(error: Throwable)
}