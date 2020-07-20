package com.example.currencyexchangerates.di

import com.example.currencyexchangerates.data.datasource.CurrencyRateRemoteDatasource
import com.example.currencyexchangerates.data.repository.CurrencyRateRepository
import com.example.currencyexchangerates.data.repository.CurrencyRateRepositoryImpl
import com.example.currencyexchangerates.domain.CurrencyRateUseCase
import com.example.currencyexchangerates.domain.utils.CurrencyRateUIMapper
import com.example.currencyexchangerates.ui.main.CurrencyRateViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class KoinModule {

    companion object {
        val appModule = module {

            single { CurrencyRateRemoteDatasource() }
            single<CurrencyRateRepository> { CurrencyRateRepositoryImpl(get()) }
            single { CurrencyRateUIMapper() }
            single { CurrencyRateUseCase(get(), get()) }
            viewModel { CurrencyRateViewModel(get()) }
        }
    }
}