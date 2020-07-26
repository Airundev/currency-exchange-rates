package com.example.currencyexchangerates.domain.utils

import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class CurrencyRateUIMapper(private val dataMapper: CurrencyRateDataMapper) {

    private val nf: NumberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)
    private val df = nf as DecimalFormat
    private val baseRate = 1.00

    init { df.applyPattern("0.00") }

    fun map(model: CurrencyRatesModel,
            baseValue: String,
            currentList: MutableList<CurrencyListCellItem>): MutableList<CurrencyListCellItem> {

        if (currentList.isEmpty()) return map(model, baseValue)

        for (item in currentList) {
            if (item.currencyCode == currentList[0].currencyCode) item.currencyRate = baseRate
            for (rate in model.rates) {
                if (rate.key == item.currencyCode) {
                    item.currencyRate = rate.value
                    item.currencyValue =  df.format(baseValue.toDouble().times(rate.value))
                    break
                }
            }
        }
        return currentList
    }

    fun updateValues(baseValue: String,
                     currentList: MutableList<CurrencyListCellItem>) : MutableList<CurrencyListCellItem> {
        for (item in currentList) {
            item.currencyValue = df.format(baseValue.toDouble().times(item.currencyRate))
        }
        return currentList
    }

    private fun map(model: CurrencyRatesModel,
                    baseValue: String): MutableList<CurrencyListCellItem> {
        val itemList = mutableListOf<CurrencyListCellItem>()
        var currencyData: Pair<Int, String> = dataMapper.getDataForCurrency(model.baseCurrency)

        itemList.add(CurrencyListCellItem(
            currencyData.first,
            model.baseCurrency,
            currencyData.second,
            baseRate,
            baseValue))

        for (rate in model.rates) {
            currencyData = dataMapper.getDataForCurrency(rate.key)
            itemList.add(CurrencyListCellItem(
                currencyData.first,
                rate.key,
                currencyData.second,
                rate.value,
                df.format(baseValue.toDouble().times(rate.value))))
        }

        return itemList
    }
}