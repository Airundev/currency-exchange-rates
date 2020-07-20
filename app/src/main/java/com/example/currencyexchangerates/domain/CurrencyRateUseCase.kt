package com.example.currencyexchangerates.domain

import com.example.currencyexchangerates.data.repository.CurrencyRateRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CurrencyRateUseCase(
    val repository: CurrencyRateRepository
) :SolidUseCaseWithParameter<String, CurrencyRateListener> {
    override fun execute(parameter: String, listener: CurrencyRateListener): Disposable {
        return repository.getCurrencies(parameter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //TODO: Implement data to UI mapper
                    //listener.onSuccess(it)
                }, {
                    listener.onError(it)
                }
            )
    }
}