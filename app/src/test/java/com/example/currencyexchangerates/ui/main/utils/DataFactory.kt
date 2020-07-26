package com.example.currencyexchangerates.ui.main.utils

import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import kotlin.math.floor

class DataFactory {

    companion object {
        fun randomString(length: Int = 10): String {
            val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            var passWord = ""
            for (i in 0 until length) {
                passWord += chars[floor(Math.random() * chars.length).toInt()]
            }
            return passWord
        }

        fun getItemList(): MutableList<CurrencyListCellItem> {
            return mutableListOf<CurrencyListCellItem>().apply {
                add(CurrencyListCellItem(0, "cur0", "currency0", 0.0, "0.00"))
                add(CurrencyListCellItem(1, "cur1", "currency1", 1.0, "1.00"))
                add(CurrencyListCellItem(2, "cur2", "currency2", 2.0, "2.00"))
                add(CurrencyListCellItem(3, "cur3", "currency4", 3.0, "3.00"))
                add(CurrencyListCellItem(4, "cur4", "currency5", 4.0, "4.00"))
            }
        }
    }
}