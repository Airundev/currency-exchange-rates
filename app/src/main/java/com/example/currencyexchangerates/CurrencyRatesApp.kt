package com.example.currencyexchangerates

import android.app.Application
import com.example.currencyexchangerates.di.KoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CurrencyRatesApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CurrencyRatesApp)
            modules(KoinModule.appModule)
        }
    }
}