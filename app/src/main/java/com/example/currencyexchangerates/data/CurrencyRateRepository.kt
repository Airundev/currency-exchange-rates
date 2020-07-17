package com.example.currencyexchangerates.data

import io.reactivex.rxjava3.core.Single

interface CurrencyRateRepository {
    //TODO: Change the String for the List of Currencies
    fun getCurrencies(param: String): Single<String>
}