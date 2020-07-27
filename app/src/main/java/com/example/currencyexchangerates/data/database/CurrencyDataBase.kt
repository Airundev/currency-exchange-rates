package com.example.currencyexchangerates.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CurrencyListItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CurrencyDataBase : RoomDatabase() {
    abstract fun currencyListItemDAO(): CurrencyListItemDAO
}