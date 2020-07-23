package com.example.currencyexchangerates.ui.components

data class CurrencyListCellItem (
    val currencyFlag: Int,
    val currencyCode: String,
    val currencyName: String,
    val currencyRate: Double
)