package com.example.currencyexchangerates.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyexchangerates.domain.CurrencyRateListener
import com.example.currencyexchangerates.domain.CurrencyRateUseCase
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import com.example.currencyexchangerates.ui.main.utils.LiveDataResult

class CurrencyRateViewModel(private val currencyRateUseCase: CurrencyRateUseCase)
    : ViewModel(), CurrencyRateListener {

    var baseCurrency = "EUR"
    var baseValue = "1.00"

    private val _liveDataResult: MutableLiveData<LiveDataResult<MutableList<CurrencyListCellItem>>> by lazy {
        MutableLiveData<LiveDataResult<MutableList<CurrencyListCellItem>>>()
    }

    val liveDataResult: LiveData<LiveDataResult<MutableList<CurrencyListCellItem>>>
        get() = _liveDataResult

    fun start() {
        _liveDataResult.postValue(LiveDataResult.loading(null))
        currencyRateUseCase.getData(baseCurrency, baseValue, this)
    }

    fun updateList(currentList: MutableList<CurrencyListCellItem>) {
        currencyRateUseCase.updateData(baseCurrency, baseValue, currentList, this)
    }

    fun updateValues(currentList: MutableList<CurrencyListCellItem>) {
        currencyRateUseCase.updateValues(baseValue, currentList, this)
    }

    override fun onSuccess(ratesList: MutableList<CurrencyListCellItem>) {
        _liveDataResult.postValue(LiveDataResult.success(ratesList))
    }

    override fun onError(error: Throwable) {
        _liveDataResult.postValue(LiveDataResult.error("", null))
    }
}