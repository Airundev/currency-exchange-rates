package com.example.currencyexchangerates.di

import com.example.currencyexchangerates.data.CurrencyRateRepository
import com.example.currencyexchangerates.domain.CurrencyRateUseCase
import com.example.currencyexchangerates.ui.main.CurrencyRateViewModel
import com.example.currencyexchangerates.utils.AppRxSchedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.dsl.module

class KoinModule {

    companion object {
        val appModule = module {

            factory { AppRxSchedulers(Schedulers.io(), AndroidSchedulers.mainThread()) }
            single { CurrencyRateRepository() }
            single { CurrencyRateUseCase(get(), get()) }
            factory { CurrencyRateViewModel(get()) }
        }
    }
}