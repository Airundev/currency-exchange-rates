package com.example.currencyexchangerates.data.repository

import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import io.reactivex.Single

interface CurrencyRateRepository {
    fun getCurrencies(param: String): Single<CurrencyRatesModel>
}