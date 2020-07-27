package com.example.currencyexchangerates.data.datasource

import com.example.currencyexchangerates.data.database.CurrencyDataBase
import com.example.currencyexchangerates.data.database.CurrencyListItemEntity
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import io.reactivex.Single
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class CurrencyRateLocalDatasource(private val currencyDatabase: CurrencyDataBase) {

    fun getCurrencyList(): Single<MutableList<CurrencyListCellItem>> {
        return currencyDatabase.currencyListItemDAO().getList().map { it.currencyListItem }
    }

    fun setCurrencyList(list: MutableList<CurrencyListCellItem>) {
        val callable = Callable {
            currencyDatabase.currencyListItemDAO().deleteList()
            currencyDatabase.currencyListItemDAO().insertAll(CurrencyListItemEntity(0, list))
        }
        val future = Executors.newSingleThreadExecutor().submit(callable)
        return future.get()
    }
}