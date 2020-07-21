package com.example.currencyexchangerates.domain.utils

import android.content.Context
import com.example.currencyexchangerates.R

class CurrencyRateDataMapper(private val context : Context) {

    fun getDataForCurrency(currency: String) : Pair<Int, String> {
        return when (currency) {
            "AUD" -> Pair(R.drawable.ic_aud, context.getString(R.string.AUD))
            "BGN" -> Pair(R.drawable.ic_bgn, context.getString(R.string.BGN))
            "BRL" -> Pair(R.drawable.ic_brl, context.getString(R.string.BRL))
            "CAD" -> Pair(R.drawable.ic_cad, context.getString(R.string.CAD))
            "CHF" -> Pair(R.drawable.ic_chf, context.getString(R.string.CHF))
            "CNY" -> Pair(R.drawable.ic_cny, context.getString(R.string.CNY))
            "CZK" -> Pair(R.drawable.ic_czk, context.getString(R.string.CZK))
            "DKK" -> Pair(R.drawable.ic_dkk, context.getString(R.string.DKK))
            "EUR" -> Pair(R.drawable.ic_eur, context.getString(R.string.EUR))
            "GBP" -> Pair(R.drawable.ic_gbp, context.getString(R.string.GBP))
            "HKD" -> Pair(R.drawable.ic_hkd, context.getString(R.string.HKD))
            "HRK" -> Pair(R.drawable.ic_hrk, context.getString(R.string.HRK))
            "HUF" -> Pair(R.drawable.ic_huf, context.getString(R.string.HUF))
            "IDR" -> Pair(R.drawable.ic_idr, context.getString(R.string.IDR))
            "ILS" -> Pair(R.drawable.ic_ils, context.getString(R.string.ILS))
            "INR" -> Pair(R.drawable.ic_inr, context.getString(R.string.INR))
            "ISK" -> Pair(R.drawable.ic_isk, context.getString(R.string.ISK))
            "JPY" -> Pair(R.drawable.ic_jpy, context.getString(R.string.JPY))
            "KRW" -> Pair(R.drawable.ic_krw, context.getString(R.string.KRW))
            "MXN" -> Pair(R.drawable.ic_mxn, context.getString(R.string.MXN))
            "MYR" -> Pair(R.drawable.ic_myr, context.getString(R.string.MYR))
            "NOK" -> Pair(R.drawable.ic_nok, context.getString(R.string.NOK))
            "NZD" -> Pair(R.drawable.ic_nzd, context.getString(R.string.NZD))
            "PHP" -> Pair(R.drawable.ic_php, context.getString(R.string.PHP))
            "PLN" -> Pair(R.drawable.ic_pln, context.getString(R.string.PLN))
            "RON" -> Pair(R.drawable.ic_ron, context.getString(R.string.RON))
            "RUB" -> Pair(R.drawable.ic_rub, context.getString(R.string.RUB))
            "SEK" -> Pair(R.drawable.ic_sek, context.getString(R.string.SEK))
            "SGD" -> Pair(R.drawable.ic_sgd, context.getString(R.string.SGD))
            "THB" -> Pair(R.drawable.ic_thb, context.getString(R.string.THB))
            "USD" -> Pair(R.drawable.ic_usd, context.getString(R.string.USD))
            "ZAR" -> Pair(R.drawable.ic_zar, context.getString(R.string.ZAR))
            else -> Pair(R.drawable.ic_unknown, context.getString(R.string.Unknown))
        }
    }
}