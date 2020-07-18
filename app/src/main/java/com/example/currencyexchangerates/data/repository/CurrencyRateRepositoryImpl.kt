package com.example.currencyexchangerates.data.repository

import com.example.currencyexchangerates.data.datasource.CurrencyRateRemoteDatasource
import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import io.reactivex.Single

class CurrencyRateRepositoryImpl(val remoteDatasource: CurrencyRateRemoteDatasource)
    : CurrencyRateRepository {

    override fun getCurrencies(param: String): Single<CurrencyRatesModel> {
        return remoteDatasource.getCurrencyRateModel(param)
    }
}