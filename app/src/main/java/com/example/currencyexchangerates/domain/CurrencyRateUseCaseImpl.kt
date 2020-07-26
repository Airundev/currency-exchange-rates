package com.example.currencyexchangerates.domain

import com.example.currencyexchangerates.data.repository.CurrencyRateRepository
import com.example.currencyexchangerates.domain.utils.CurrencyRateUIMapper
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class CurrencyRateUseCaseImpl(
    private val repository: CurrencyRateRepository,
    private val mapper: CurrencyRateUIMapper,
    private val backgroundThread: Scheduler,
    private val mainThread: Scheduler
) : CurrencyRateUseCase {

    override fun updateData(baseCurrency: String,
                            baseValue: String,
                            currentList: MutableList<CurrencyListCellItem>,
                            listener: CurrencyRateListener): Disposable {
        return repository.getCurrencies(baseCurrency)
            .subscribeOn(backgroundThread)
            .observeOn(mainThread)
            .subscribe(
                {
                    listener.onSuccess(mapper.map(it, baseValue, currentList))
                }, {
                    listener.onError(it)
                }
            )
    }

    override fun updateValues(baseValue: String,
                     currentList: MutableList<CurrencyListCellItem>,
                     listener: CurrencyRateListener) {
        listener.onSuccess(mapper.updateValues(baseValue, currentList))
    }
}