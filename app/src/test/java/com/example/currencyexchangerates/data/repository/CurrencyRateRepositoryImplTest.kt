package com.example.currencyexchangerates.data.repository

import com.example.currencyexchangerates.data.datasource.CurrencyRateLocalDatasource
import com.example.currencyexchangerates.data.datasource.CurrencyRateRemoteDatasource
import com.example.currencyexchangerates.utils.DataFactory.Companion.getItemList
import com.example.currencyexchangerates.utils.DataFactory.Companion.getRatesModel
import com.example.currencyexchangerates.utils.DataFactory.Companion.randomString
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CurrencyRateRepositoryImplTest {

    private val remoteDatasource: CurrencyRateRemoteDatasource = mock()
    private val localDatasource: CurrencyRateLocalDatasource = mock()

    private lateinit var repository: CurrencyRateRepository

    @BeforeEach
    fun setUp() {
        repository = CurrencyRateRepositoryImpl(remoteDatasource, localDatasource)
    }

    @Test
    fun `should fetch currencies from database`() {
        doReturn(Single.just(getItemList())).whenever(localDatasource).getCurrencyList()

        repository.getLocalCurrencies()

        verify(localDatasource).getCurrencyList()
    }

    @Test
    fun `should fetch currencies from service`() {
        doReturn(Single.just(getRatesModel())).whenever(localDatasource).getCurrencyList()

        repository.getCurrencies(randomString())

        verify(remoteDatasource).getCurrencyRateModel(any())
    }

    @Test
    fun `should save list to database`() {
        repository.setCurrencies(getItemList())

        verify(localDatasource).setCurrencyList(any())
    }
}