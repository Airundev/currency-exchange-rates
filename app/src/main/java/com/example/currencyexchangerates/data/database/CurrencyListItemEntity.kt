package com.example.currencyexchangerates.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem

@Entity
data class CurrencyListItemEntity (
    @PrimaryKey
    @ColumnInfo(name = "currencyListItem") val currencyListItem: MutableList<CurrencyListCellItem>
)