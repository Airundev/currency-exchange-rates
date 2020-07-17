package com.example.currencyexchangerates.utils

import io.reactivex.rxjava3.core.Scheduler

data class AppRxSchedulers(
    val database: Scheduler,
    val disk: Scheduler,
    val network: Scheduler,
    val background: Scheduler,
    val main: Scheduler
)