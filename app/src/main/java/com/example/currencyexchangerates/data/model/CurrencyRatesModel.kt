package com.example.currencyexchangerates.data.model

import com.google.gson.annotations.SerializedName

class CurrencyRatesModel(
    @SerializedName("baseCurrency") val baseCurrency: String,
    @SerializedName("rates") val rates: Map<String, Double>
)