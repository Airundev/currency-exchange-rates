package com.example.currencyexchangerates.domain

import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import io.reactivex.disposables.Disposable


interface CurrencyRateUseCase {
    fun getData(baseCurrency: String,
                baseValue: String,
                listener: CurrencyRateListener): Disposable

    fun updateData(baseCurrency: String,
                   baseValue: String,
                   currentList: MutableList<CurrencyListCellItem>,
                   listener: CurrencyRateListener): Disposable

    fun updateValues(baseValue: String,
                     currentList: MutableList<CurrencyListCellItem>,
                     listener: CurrencyRateListener)
}