package com.example.currencyexchangerates.data.datasource

import com.example.currencyexchangerates.data.ServiceBuilder
import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import io.reactivex.Single

class CurrencyRateRemoteDatasource: CurrencyRateDatasource {

    override fun getCurrencyRateModel(param: String): Single<CurrencyRatesModel> {
        return ServiceBuilder.buildService().getCurrencies(param)
    }
}