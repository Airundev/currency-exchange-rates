package com.example.currencyexchangerates.domain

import com.example.currencyexchangerates.data.CurrencyRateRepository
import com.example.currencyexchangerates.utils.AppRxSchedulers
import io.reactivex.rxjava3.disposables.Disposable

class CurrencyRateUseCase(
    val repository: CurrencyRateRepository,
    val rxSchedulers: AppRxSchedulers
) :SolidUseCaseWithParameter<String, CurrencyRateListener> {
    override fun execute(parameter: String, listener: CurrencyRateListener): Disposable {
        return repository.getCurrencies(parameter)
            .subscribeOn(rxSchedulers.background)
            .observeOn(rxSchedulers.main)
            .subscribe(
                {
                    //TODO: Make it so success receives a list of currencies
                    listener.onSuccess()
                }, {
                    //TODO: Fix this
                    listener.onError(Throwable())
                }
            )
    }
}