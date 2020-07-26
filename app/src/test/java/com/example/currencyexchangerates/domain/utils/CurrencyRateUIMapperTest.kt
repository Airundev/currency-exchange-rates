package com.example.currencyexchangerates.domain.utils

import com.example.currencyexchangerates.data.model.CurrencyRatesModel
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import com.example.currencyexchangerates.utils.DataFactory.Companion.getCurrencyData
import com.example.currencyexchangerates.utils.DataFactory.Companion.randomInt
import com.example.currencyexchangerates.utils.DataFactory.Companion.randomString
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CurrencyRateUIMapperTest {

    private val dataMapper: CurrencyRateDataMapper = mock()

    private lateinit var uiMapper: CurrencyRateUIMapper

    @BeforeEach
    fun setUp() {
        uiMapper = CurrencyRateUIMapper(dataMapper)
    }

    @Test
    fun `should map itemList when currentList is empty`() {
        val list = getComparableList()
        ArrangeBuilder().withDataMapperResult()

        val mapped = uiMapper.map(getComparableModel(), mockValue, mutableListOf())

        assertEquals(mapped[0].currencyCode, list[0].currencyCode)
        assertEquals(mapped[0].currencyRate, list[0].currencyRate)
        assertEquals(mapped[1].currencyCode, list[1].currencyCode)
        assertEquals(mapped[1].currencyCode, list[1].currencyCode)
    }

    @Test
    fun `should map itemList with a filled currentList`() {
        val list = getComparableList()
        ArrangeBuilder().withDataMapperResult()

        val mapped = uiMapper.map(getComparableModel(), mockValue, list)

        assertEquals(mapped[0].currencyCode, list[0].currencyCode)
        assertEquals(mapped[0].currencyRate, list[0].currencyRate)
        assertEquals(mapped[1].currencyCode, list[1].currencyCode)
        assertEquals(mapped[1].currencyCode, list[1].currencyCode)
    }

    @Test
    fun `should map item values`() {
        val list = getComparableList()

        val mapped = uiMapper.updateValues(mockValue, list)

        assertEquals(mapped[0].currencyValue, list[0].currencyValue)
        assertEquals(mapped[1].currencyValue, list[1].currencyValue)
    }

    inner class ArrangeBuilder {
        fun withDataMapperResult() {
            doAnswer { getCurrencyData() }.whenever(dataMapper).getDataForCurrency(any())
        }
    }

    private fun getComparableList(): MutableList<CurrencyListCellItem> {
        return mutableListOf<CurrencyListCellItem>().apply {
            add(CurrencyListCellItem(randomInt(), baseCurrencyCode, randomString(), baseCurrencyRate, mockValue))
            add(CurrencyListCellItem(randomInt(), mockCurrencyCode, randomString(), mockCurrencyRate, mockValue))
        }
    }

    private fun getComparableModel(): CurrencyRatesModel {
        val map = mutableMapOf<String, Double>().apply {
            this[mockCurrencyCode] = mockCurrencyRate
        }
        return CurrencyRatesModel(baseCurrencyCode, map)
    }

    companion object {
        private const val baseCurrencyCode = "A"
        private const val baseCurrencyRate = 1.0
        private const val mockCurrencyCode = "B"
        private const val mockCurrencyRate = 1.23
        private const val mockValue = "13.14"
    }
}