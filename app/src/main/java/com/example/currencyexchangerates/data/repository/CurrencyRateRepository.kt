package com.example.currencyexchangerates.data.repository

import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import io.reactivex.Single

interface CurrencyRateRepository {
    fun getLocalCurrencies(): Single<MutableList<CurrencyListCellItem>>
    fun getCurrencies(param: String): Single<CurrencyRatesModel>
    fun setCurrencies(list: MutableList<CurrencyListCellItem>)
}