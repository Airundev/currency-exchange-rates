package com.example.currencyexchangerates.data.datasource

import com.example.currencyexchangerates.data.CurrencyService
import com.example.currencyexchangerates.utils.DataFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CurrencyRateRemoteDatasourceTest {

    private val currencyService: CurrencyService = mock()

    private lateinit var datasource: CurrencyRateRemoteDatasource

    @BeforeEach
    fun setUp() {
        datasource = CurrencyRateRemoteDatasource(currencyService)
    }

    @Test
    fun `should fetch currencies from datasource`() {
        doReturn(Single.just(DataFactory.getRatesModel())).whenever(currencyService).getCurrencies(any())

        datasource.getCurrencyRateModel(DataFactory.randomString())

        verify(currencyService).getCurrencies(any())
    }
}