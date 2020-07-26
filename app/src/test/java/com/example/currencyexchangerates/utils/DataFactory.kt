package com.example.currencyexchangerates.utils

import com.example.currencyexchangerates.data.model.CurrencyRatesModel
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

        fun randomInt(bound: Int = 1000 + 1): Int {
            return (1..bound).shuffled().first()
        }

        private fun randomDouble(): Double {
            return Math.random()
        }

        fun getItemList(): MutableList<CurrencyListCellItem> {
            return mutableListOf<CurrencyListCellItem>().apply {
                add(CurrencyListCellItem(randomInt(), randomString(), randomString(), randomDouble(), randomString()))
                add(CurrencyListCellItem(randomInt(), randomString(), randomString(), randomDouble(), randomString()))
                add(CurrencyListCellItem(randomInt(), randomString(), randomString(), randomDouble(), randomString()))
                add(CurrencyListCellItem(randomInt(), randomString(), randomString(), randomDouble(), randomString()))
                add(CurrencyListCellItem(randomInt(), randomString(), randomString(), randomDouble(), randomString()))
            }
        }

        fun getCurrencyData(): Pair<Int, String> {
            return Pair(randomInt(), randomString())
        }

        fun getRatesModel(): CurrencyRatesModel {
            val map = mutableMapOf<String, Double>().apply {
                this[randomString()] = randomDouble()
                this[randomString()] = randomDouble()
            }
            return CurrencyRatesModel(randomString(), map)
        }
    }
}