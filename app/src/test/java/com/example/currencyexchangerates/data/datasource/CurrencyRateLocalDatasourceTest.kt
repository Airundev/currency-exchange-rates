package com.example.currencyexchangerates.data.datasource

import com.example.currencyexchangerates.data.database.CurrencyListItemDAO
import com.example.currencyexchangerates.data.database.CurrencyListItemEntity
import com.example.currencyexchangerates.utils.DataFactory.Companion.getItemList
import com.example.currencyexchangerates.utils.DataFactory.Companion.randomInt
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CurrencyRateLocalDatasourceTest {

    private val currencyDatabase: CurrencyListItemDAO = mock()

    private lateinit var datasource: CurrencyRateLocalDatasource

    @BeforeEach
    fun setUp() {
        datasource = CurrencyRateLocalDatasource(currencyDatabase)
    }

    @Test
    fun `should fetch currencies from database`() {
        doReturn(Single.just(CurrencyListItemEntity(randomInt(), getItemList()))).whenever(currencyDatabase).getList()

        datasource.getCurrencyList()

        verify(currencyDatabase).getList()
    }

    @Test
    fun `should set currencies to database`() {

        datasource.setCurrencyList(getItemList())

        inOrder(currencyDatabase) {
            verify(currencyDatabase).deleteList()
            verify(currencyDatabase).insertAll(any())
        }
    }
}