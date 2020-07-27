package com.example.currencyexchangerates.domain

import com.example.currencyexchangerates.data.repository.CurrencyRateRepository
import com.example.currencyexchangerates.domain.utils.CurrencyRateUIMapper
import com.example.currencyexchangerates.utils.DataFactory.Companion.getItemList
import com.example.currencyexchangerates.utils.DataFactory.Companion.getRatesModel
import com.example.currencyexchangerates.utils.DataFactory.Companion.randomString
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class CurrencyRateUseCaseImplTest {

    private val currencyRateRepository: CurrencyRateRepository = mock()
    private val currencyRateUIMapper: CurrencyRateUIMapper = mock()
    private val listener: CurrencyRateListener = mock()

    private lateinit var currencyRateUseCase: CurrencyRateUseCase

    private val testScheduler = TestScheduler()

    @BeforeEach
    fun setup() {
        currencyRateUseCase = CurrencyRateUseCaseImpl(currencyRateRepository, currencyRateUIMapper, testScheduler)
    }

    @AfterEach
    fun tearDown() {
        reset(currencyRateRepository, currencyRateUIMapper, listener)
    }

    @Test
    fun `should return data if getLocalCurrencies is successful`() {
        ArrangeBuilder().withLocalCurrenciesSuccess()

        currencyRateUseCase.getData(randomString(), randomString(), getItemList(), listener)
        testScheduler.triggerActions()

        inOrder(currencyRateRepository, listener) {
            verify(currencyRateRepository).getLocalCurrencies()
            verify(listener).onSuccess(any())
        }
    }

    @Test
    fun `should return mapped data if getLocalCurrencies fails and updateData is successful`() {
        ArrangeBuilder().withLocalCurrenciesError()
        ArrangeBuilder().withRepositorySuccess()

        currencyRateUseCase.getData(randomString(), randomString(), getItemList(), listener)
        testScheduler.triggerActions()

        inOrder(currencyRateRepository, listener) {
            verify(currencyRateRepository).getLocalCurrencies()
            verify(currencyRateRepository).getCurrencies(any())
            verify(listener).onSuccess(any())
        }
    }

    @Test
    fun `should return error if getLocalCurrencies fails and updateData fails`() {
        ArrangeBuilder().withLocalCurrenciesError()
        ArrangeBuilder().withRepositoryError()

        currencyRateUseCase.getData(randomString(), randomString(), getItemList(), listener)
        testScheduler.triggerActions()

        inOrder(currencyRateRepository, listener) {
            verify(currencyRateRepository).getLocalCurrencies()
            verify(currencyRateRepository).getCurrencies(any())
            verify(listener).onError(any())
        }
    }

    @Test
    fun `should return mapped data if updateData call is successful`() {
        ArrangeBuilder().withRepositorySuccess()

        currencyRateUseCase.updateData(randomString(), randomString(), getItemList(), listener)
        testScheduler.triggerActions()

        inOrder(currencyRateRepository, currencyRateUIMapper, listener) {
            verify(currencyRateRepository).getCurrencies(any())
            verify(currencyRateUIMapper).map(any(), any(), any())
            verify(listener).onSuccess(any())
        }
    }

    @Test
    fun `should return error if updateData call fails`() {
        ArrangeBuilder().withRepositoryError()

        currencyRateUseCase.updateData(randomString(), randomString(), getItemList(), listener)
        testScheduler.triggerActions()

        inOrder(currencyRateRepository, listener) {
            verify(currencyRateRepository).getCurrencies(any())
            verify(listener).onError(any())
        }
    }

    @Test
    fun `should return mapped data if updateValues is called`() {
        ArrangeBuilder().withMappedValues()

        currencyRateUseCase.updateValues(randomString(), getItemList(), listener)

        inOrder(currencyRateUIMapper, currencyRateRepository, listener) {
            verify(currencyRateUIMapper).updateValues(any(), any())
            verify(currencyRateRepository).setCurrencies(any())
            verify(listener).onSuccess(any())
        }
    }

    inner class ArrangeBuilder {
        fun withLocalCurrenciesSuccess(): ArrangeBuilder {
            doReturn(Single.just(getItemList())).whenever(currencyRateRepository).getLocalCurrencies()
            return this
        }

        fun withLocalCurrenciesError(): ArrangeBuilder {
            doReturn(Single.error<Throwable>(Throwable())).whenever(currencyRateRepository).getLocalCurrencies()
            return this
        }

        fun withRepositorySuccess(): ArrangeBuilder {
            doReturn(Single.just(getRatesModel())).whenever(currencyRateRepository).getCurrencies(any())
            return this
        }

        fun withRepositoryError(): ArrangeBuilder {
            doReturn(Single.error<Throwable>(Throwable())).whenever(currencyRateRepository).getCurrencies(any())
            return this
        }

        fun withMappedValues(): ArrangeBuilder {
            doAnswer { getItemList() }.whenever(currencyRateUIMapper).updateValues(any(), any())
            return this
        }
    }
}