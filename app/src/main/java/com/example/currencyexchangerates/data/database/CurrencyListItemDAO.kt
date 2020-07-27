package com.example.currencyexchangerates.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface CurrencyListItemDAO {
    @Query("SELECT * FROM currencyListItemEntity")
    fun getList(): Single<CurrencyListItemEntity>

    @Query("DELETE FROM currencyListItemEntity")
    fun deleteList()

    @Insert
    fun insertAll(vararg currencyList: CurrencyListItemEntity)
}