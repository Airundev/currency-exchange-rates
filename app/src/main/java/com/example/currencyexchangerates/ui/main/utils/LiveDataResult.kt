package com.example.currencyexchangerates.ui.main.utils

/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

data class LiveDataResult<out T> (
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {

    enum class Status {
        SUCCESS,
        LOADING,
        ERROR,
    }

    companion object {
        fun <T> success(data: T): LiveDataResult<T> {
            return LiveDataResult(Status.SUCCESS, data)
        }

        fun <T> loading(data: T? = null): LiveDataResult<T> {
            return LiveDataResult(Status.LOADING, data)
        }

        fun <T> error(message: String? = null, data: T? = null): LiveDataResult<T> {
            return LiveDataResult(Status.ERROR, data, message)
        }
    }
}
