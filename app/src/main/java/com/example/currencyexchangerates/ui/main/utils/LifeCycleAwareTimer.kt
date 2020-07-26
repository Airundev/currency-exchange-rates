package com.example.currencyexchangerates.ui.main.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.*

class LifecycleAwareTimer(private val onTick: () -> Unit) : LifecycleObserver {

    companion object {
        private const val TIMER_DELAY = 1000L
    }

    private var timer: Timer? = null
    var canTick = false
    var canRefresh = false

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        canTick = true
        canRefresh = false
        timer = Timer().apply { scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                onTick()
            } }, 0, TIMER_DELAY) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        canTick = false
        canRefresh = true
        timer?.apply {
            cancel()
            purge()
        }
    }
}