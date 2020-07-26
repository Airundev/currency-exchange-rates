package com.example.currencyexchangerates.domain.utils

import android.content.Context
import com.example.currencyexchangerates.R
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CurrencyRateDataMapperTest {

    private val context: Context = mock()

    private lateinit var dataMapper: CurrencyRateDataMapper

    @BeforeEach
    fun setup() {
        dataMapper = CurrencyRateDataMapper(context)

        doReturn(CURRENCY_NAME).whenever(context).getString(any())
    }

    @Test
    fun `should map flag and name for given currency`() {
        val data = Pair(CURRENCY_FLAG, CURRENCY_NAME)

        val result = dataMapper.getDataForCurrency(CURRENCY)

        assertEquals(result, data)
    }

    companion object {
        private const val CURRENCY = "AUD"
        private const val CURRENCY_NAME = "AUD"
        private const val CURRENCY_FLAG = R.drawable.ic_aud
    }
}