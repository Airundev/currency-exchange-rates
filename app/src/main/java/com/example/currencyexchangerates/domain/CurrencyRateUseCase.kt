package com.example.currencyexchangerates.domain

import com.example.currencyexchangerates.data.repository.CurrencyRateRepository
import com.example.currencyexchangerates.domain.utils.CurrencyRateUIMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CurrencyRateUseCase(
    private val repository: CurrencyRateRepository,
    private val mapper: CurrencyRateUIMapper
) :SolidUseCaseWithParameter<String, CurrencyRateListener> {
    override fun execute(parameter: String, listener: CurrencyRateListener): Disposable {
        return repository.getCurrencies(parameter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    listener.onSuccess(mapper.map(it))
                }, {
                    listener.onError(it)
                }
            )
    }
}