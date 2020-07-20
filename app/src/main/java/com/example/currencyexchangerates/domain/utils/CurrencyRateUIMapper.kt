package com.example.currencyexchangerates.domain.utils

import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem

class CurrencyRateUIMapper() {

    fun map(model: CurrencyRatesModel): List<CurrencyListCellItem> {
        val itemList = mutableListOf<CurrencyListCellItem>()
        //TODO: Prepare it for any flag, name and map the correct description
        itemList.add(CurrencyListCellItem(R.drawable.ic_eur, model.baseCurrency, "Euro", "1"))
        for (rate in model.rates) {
            itemList.add(CurrencyListCellItem(R.drawable.ic_eur, rate.key, "LUL", rate.value.toString()))
        }
        return itemList
    }
}