package com.example.currencyexchangerates.data

interface CurrencyUseCaseListener {

    fun onCurrencySuccess()
    fun onCurrencyError()
}