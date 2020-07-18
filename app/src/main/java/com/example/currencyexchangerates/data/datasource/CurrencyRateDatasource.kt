package com.example.currencyexchangerates.data.datasource

import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import io.reactivex.Single

interface CurrencyRateDatasource {
    fun getCurrencyRateModel(param: String): Single<CurrencyRatesModel>
}