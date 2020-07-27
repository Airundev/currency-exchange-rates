package com.example.currencyexchangerates.data.repository

import com.example.currencyexchangerates.data.datasource.CurrencyRateLocalDatasource
import com.example.currencyexchangerates.data.datasource.CurrencyRateRemoteDatasource
import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import io.reactivex.Single

class CurrencyRateRepositoryImpl(private val remoteDatasource: CurrencyRateRemoteDatasource,
                                 private val localDatasource: CurrencyRateLocalDatasource)
    : CurrencyRateRepository {

    override fun getLocalCurrencies(): Single<MutableList<CurrencyListCellItem>> {
        return localDatasource.getCurrencyList()
    }

    override fun getCurrencies(param: String): Single<CurrencyRatesModel> {
        return remoteDatasource.getCurrencyRateModel(param)
    }

    override fun setCurrencies(list: MutableList<CurrencyListCellItem>) {
        localDatasource.setCurrencyList(list)
    }
}