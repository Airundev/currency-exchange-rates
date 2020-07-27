package com.example.currencyexchangerates.domain

import com.example.currencyexchangerates.data.repository.CurrencyRateRepository
import com.example.currencyexchangerates.domain.utils.CurrencyRateUIMapper
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class CurrencyRateUseCaseImpl(
    private val repository: CurrencyRateRepository,
    private val mapper: CurrencyRateUIMapper,
    private val backgroundThread: Scheduler
) : CurrencyRateUseCase {

    override fun getData(baseCurrency: String,
                baseValue: String,
                currentList: MutableList<CurrencyListCellItem>,
                listener: CurrencyRateListener): Disposable {
        return repository.getLocalCurrencies()
            .subscribeOn(backgroundThread)
            .subscribe(
                {
                    listener.onSuccess(it)
                }, {
                    updateData(baseCurrency, baseValue, currentList, listener)
                }
            )
    }

    override fun updateData(baseCurrency: String,
                            baseValue: String,
                            currentList: MutableList<CurrencyListCellItem>,
                            listener: CurrencyRateListener): Disposable {
        return repository.getCurrencies(baseCurrency)
            .subscribeOn(backgroundThread)
            .map { mapper.map(it, baseValue, currentList) }
            .subscribe(
                {
                    repository.setCurrencies(it)
                    listener.onSuccess(it)
                }, {
                    listener.onError(it)
                }
            )
    }

    override fun updateValues(baseValue: String,
                     currentList: MutableList<CurrencyListCellItem>,
                     listener: CurrencyRateListener) {
        val updatedList = mapper.updateValues(baseValue, currentList)
        repository.setCurrencies(updatedList)
        listener.onSuccess(updatedList)
    }
}