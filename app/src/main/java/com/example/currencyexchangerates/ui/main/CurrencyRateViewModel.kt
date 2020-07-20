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

    private val _liveDataResult: MutableLiveData<LiveDataResult<List<CurrencyListCellItem>>> by lazy {
        MutableLiveData<LiveDataResult<List<CurrencyListCellItem>>>()
    }

    val liveDataResult: LiveData<LiveDataResult<List<CurrencyListCellItem>>>
        get() = _liveDataResult

    fun updateList() {
        _liveDataResult.postValue(LiveDataResult.loading(null))
        currencyRateUseCase.execute(BASE_CURRENCY, this)
    }

    override fun onSuccess(ratesList: List<CurrencyListCellItem>) {
        _liveDataResult.postValue(LiveDataResult.success(ratesList))
    }

    override fun onError(error: Throwable) {
        _liveDataResult.postValue(LiveDataResult.error("", null))
    }

    companion object {
        const val BASE_CURRENCY: String = "EUR"
    }
}