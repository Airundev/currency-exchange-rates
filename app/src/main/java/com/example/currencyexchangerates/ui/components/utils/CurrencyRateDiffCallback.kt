package com.example.currencyexchangerates.ui.components.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem

class CurrencyRateDiffCallback(
    private val newCurrencies: List<CurrencyListCellItem>,
    private val oldCurrencies: List<CurrencyListCellItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldCurrencies.size
    }

    override fun getNewListSize(): Int {
        return newCurrencies.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCurrencies[oldItemPosition] == newCurrencies[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCurrencies[oldItemPosition].currencyValue == newCurrencies[newItemPosition].currencyValue
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? =
        CurrencyValueDifference(newCurrencies[newItemPosition].currencyValue)
}

data class CurrencyValueDifference(val newCurrencyValue: String?)