package com.example.currencyexchangerates.domain

import com.example.currencyexchangerates.ui.components.CurrencyListCellItem

interface CurrencyRateListener {
    fun onSuccess(ratesList: List<CurrencyListCellItem>)
    fun onError(error: Throwable)
}