package com.example.currencyexchangerates.data

import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyRateRepositoryImpl: CurrencyRateRepository {
    override fun getCurrencies(param: String): Single<String> {
        val baseUrl = "https://hiring.revolut.codes/api/android/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CurrencyService::class.java)
        val call = service.getCurrencies(param)

        return Single.create { emitter: SingleEmitter<String>? ->

        }
    }
}