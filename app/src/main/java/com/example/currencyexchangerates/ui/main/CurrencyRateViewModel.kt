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

    private var baseCurrency: String = BASE_CURRENCY
    private var baseValue: String = BASE_VALUE
    private var currentList: MutableList<CurrencyListCellItem> = mutableListOf()

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
        if (currentList.isEmpty()) {
            _liveDataResult.postValue(LiveDataResult.loading(null))
            currencyRateUseCase.getData(baseCurrency, baseValue, currentList, this)
        } else {
            currencyRateUseCase.updateData(baseCurrency, baseValue, currentList, this)
        }
    }

    fun updateValues(baseValue: String) {
        this.baseValue = baseValue
        currencyRateUseCase.updateValues(baseValue, currentList, this)
    }

    override fun onSuccess(ratesList: MutableList<CurrencyListCellItem>) {
        updateBaseData(ratesList[0].currencyCode, ratesList[0].currencyValue, ratesList)
        _liveDataResult.postValue(LiveDataResult.success(ratesList))
    }

    override fun onError(error: Throwable) {
        _liveDataResult.postValue(LiveDataResult.error("", null))
    }

    companion object {
        private const val BASE_CURRENCY = "EUR"
        private const val BASE_VALUE = "1.00"
    }
}