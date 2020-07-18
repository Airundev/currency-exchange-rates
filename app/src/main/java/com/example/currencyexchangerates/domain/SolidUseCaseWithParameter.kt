package com.example.currencyexchangerates.domain

import io.reactivex.disposables.Disposable


interface SolidUseCaseWithParameter<R, T> {
    fun execute(parameter: R, listener: T): Disposable
}