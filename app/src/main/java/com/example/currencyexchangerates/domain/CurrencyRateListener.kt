package com.example.currencyexchangerates.domain

interface CurrencyRateListener {
    fun onSuccess()
    fun onError(error: Throwable)
}