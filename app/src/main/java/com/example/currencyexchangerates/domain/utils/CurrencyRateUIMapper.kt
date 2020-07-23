package com.example.currencyexchangerates.domain.utils

import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem

class CurrencyRateUIMapper(private val dataMapper: CurrencyRateDataMapper) {

    fun map(model: CurrencyRatesModel): MutableList<CurrencyListCellItem> {
        val itemList = mutableListOf<CurrencyListCellItem>()
        var currencyData: Pair<Int, String> = dataMapper.getDataForCurrency(model.baseCurrency)

        itemList.add(CurrencyListCellItem(
            currencyData.first,
            model.baseCurrency,
            currencyData.second,
            1.00))

        for (rate in model.rates) {
            currencyData = dataMapper.getDataForCurrency(rate.key)
            itemList.add(CurrencyListCellItem(
                currencyData.first,
                rate.key,
                currencyData.second,
                rate.value))
        }

        return itemList
    }
}