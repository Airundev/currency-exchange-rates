package com.example.currencyexchangerates.domain

import com.example.currencyexchangerates.ui.components.CurrencyListCellItem

interface CurrencyRateListener {
    fun onSuccess(ratesList: MutableList<CurrencyListCellItem>)
    fun onError(error: Throwable)
}