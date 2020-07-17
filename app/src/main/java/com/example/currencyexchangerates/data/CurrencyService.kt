package com.example.currencyexchangerates.data

import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("latest")
    fun getCurrencies(@Query("base") base: String): Single<CurrencyRatesModel>
}