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

    private lateinit var baseCurrency: String
    private lateinit var baseValue: String
    private lateinit var currentList: MutableList<CurrencyListCellItem>

    private val _liveDataResult: MutableLiveData<LiveDataResult<MutableList<CurrencyListCellItem>>> by lazy {
        MutableLiveData<LiveDataResult<MutableList<CurrencyListCellItem>>>()
    }

    val liveDataResult: LiveData<LiveDataResult<MutableList<CurrencyListCellItem>>>
        get() = _liveDataResult

    fun updateBaseData(baseCurrency: String, baseValue: String, currentList: MutableList<CurrencyListCellItem>) {
        this.baseCurrency = baseCurrency
        this.baseValue = baseValue
        this.currentList = currentList
    }

    fun updateList() {
        if (currentList.isEmpty()) _liveDataResult.postValue(LiveDataResult.loading(null))
        currencyRateUseCase.updateData(baseCurrency, baseValue, currentList, this)
    }

    fun updateValues(baseValue: String) {
        this.baseValue = baseValue
        currencyRateUseCase.updateValues(baseValue, currentList, this)
    }

    override fun onSuccess(ratesList: MutableList<CurrencyListCellItem>) {
        currentList = ratesList
        _liveDataResult.postValue(LiveDataResult.success(ratesList))
    }

    override fun onError(error: Throwable) {
        _liveDataResult.postValue(LiveDataResult.error("", null))
    }
}