package com.example.currencyexchangerates.data.datasource

import com.example.currencyexchangerates.data.CurrencyService
import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import io.reactivex.Single

class CurrencyRateRemoteDatasource(private val currencyService: CurrencyService): CurrencyRateDatasource {

    override fun getCurrencyRateModel(param: String): Single<CurrencyRatesModel> {
        return currencyService.getCurrencies(param)
    }
}