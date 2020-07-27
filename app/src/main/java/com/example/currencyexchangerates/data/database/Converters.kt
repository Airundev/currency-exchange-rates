package com.example.currencyexchangerates.data.database

import androidx.room.TypeConverter
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromItemList(list: MutableList<CurrencyListCellItem>): String {
       list.let {
           return Gson().toJson(list, object: TypeToken<MutableList<CurrencyListCellItem>>(){}.type)
       }
    }

    @TypeConverter
    fun fromItemList(value: String): MutableList<CurrencyListCellItem> {
        value.let {
            return Gson().fromJson(value, object: TypeToken<MutableList<CurrencyListCellItem>>(){}.type)
        }
    }
}