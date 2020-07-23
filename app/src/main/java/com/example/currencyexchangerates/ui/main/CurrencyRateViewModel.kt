package com.example.currencyexchangerates.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyexchangerates.domain.CurrencyRateListener
import com.example.currencyexchangerates.domain.CurrencyRateUseCase
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import com.example.currencyexchangerates.ui.main.utils.LiveDataResult

class CurrencyRateViewModel(
    private val currencyRateUseCase: CurrencyRateUseCase
) : ViewModel(), CurrencyRateListener {

    private val _liveDataResult: MutableLiveData<LiveDataResult<MutableList<CurrencyListCellItem>>> by lazy {
        MutableLiveData<LiveDataResult<MutableList<CurrencyListCellItem>>>()
    }

    val liveDataResult: LiveData<LiveDataResult<MutableList<CurrencyListCellItem>>>
        get() = _liveDataResult

    fun start(baseCurrency: String) {
        _liveDataResult.postValue(LiveDataResult.loading(null))
        currencyRateUseCase.execute(baseCurrency, this)
    }

    fun updateList(baseCurrency: String) {
        currencyRateUseCase.execute(baseCurrency, this)
    }

    override fun onSuccess(ratesList: MutableList<CurrencyListCellItem>) {
        _liveDataResult.postValue(LiveDataResult.success(ratesList))
    }

    override fun onError(error: Throwable) {
        _liveDataResult.postValue(LiveDataResult.error("", null))
    }
}