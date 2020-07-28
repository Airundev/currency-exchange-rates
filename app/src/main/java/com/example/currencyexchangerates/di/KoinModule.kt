package com.example.currencyexchangerates.di

import androidx.room.Room
import com.example.currencyexchangerates.data.ServiceBuilder
import com.example.currencyexchangerates.data.database.CurrencyDataBase
import com.example.currencyexchangerates.data.datasource.CurrencyRateLocalDatasource
import com.example.currencyexchangerates.data.datasource.CurrencyRateRemoteDatasource
import com.example.currencyexchangerates.data.repository.CurrencyRateRepository
import com.example.currencyexchangerates.data.repository.CurrencyRateRepositoryImpl
import com.example.currencyexchangerates.domain.CurrencyRateUseCase
import com.example.currencyexchangerates.domain.CurrencyRateUseCaseImpl
import com.example.currencyexchangerates.domain.utils.CurrencyRateDataMapper
import com.example.currencyexchangerates.domain.utils.CurrencyRateUIMapper
import com.example.currencyexchangerates.ui.main.CurrencyRateViewModel
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class KoinModule {

    companion object {
        private const val DATABASE = "currencyDatabase"
        val appModule = module {
            single { Room.databaseBuilder(androidContext(), CurrencyDataBase::class.java, DATABASE).build().currencyListItemDAO() }
            single { CurrencyRateDataMapper(androidContext()) }
            single { CurrencyRateUIMapper(get()) }
            single { ServiceBuilder.buildService() }
            single { CurrencyRateRemoteDatasource(get()) }
            single { CurrencyRateLocalDatasource(get())}
            single<CurrencyRateRepository> { CurrencyRateRepositoryImpl(get(), get()) }
            single<CurrencyRateUseCase> { CurrencyRateUseCaseImpl(get(), get(), Schedulers.io()) }
            viewModel { CurrencyRateViewModel(get()) }
        }
    }
}