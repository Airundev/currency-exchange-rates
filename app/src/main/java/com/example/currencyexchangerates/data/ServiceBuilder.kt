package com.example.currencyexchangerates.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val client = OkHttpClient.Builder().build()
    private const val BASE_URL = "https://hiring.revolut.codes/api/android/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
        .create(CurrencyService::class.java)

    fun buildService(): CurrencyService {
        return retrofit
    }
}