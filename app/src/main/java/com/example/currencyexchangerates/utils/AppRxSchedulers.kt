package com.example.currencyexchangerates.utils

import io.reactivex.rxjava3.core.Scheduler

data class AppRxSchedulers(
    val background: Scheduler,
    val main: Scheduler
)