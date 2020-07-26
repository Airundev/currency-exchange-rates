package com.example.currencyexchangerates.data.repository

import com.example.currencyexchangerates.data.datasource.CurrencyRateRemoteDatasource
import com.example.currencyexchangerates.utils.DataFactory.Companion.getRatesModel
import com.example.currencyexchangerates.utils.DataFactory.Companion.randomString
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CurrencyRateRepositoryImplTest {

    private val datasource: CurrencyRateRemoteDatasource = mock()

    private lateinit var repository: CurrencyRateRepository

    @BeforeEach
    fun setUp() {
        repository = CurrencyRateRepositoryImpl(datasource)
    }

    @Test
    fun `should fetch currencies from datasource`() {
        doReturn(Single.just(getRatesModel())).whenever(datasource).getCurrencyRateModel(any())

        repository.getCurrencies(randomString())

        verify(datasource).getCurrencyRateModel(any())
    }
}