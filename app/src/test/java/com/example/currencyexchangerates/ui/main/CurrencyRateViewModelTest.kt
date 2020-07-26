package com.example.currencyexchangerates.ui.main

import androidx.lifecycle.Observer
import com.example.currencyexchangerates.domain.CurrencyRateListener
import com.example.currencyexchangerates.domain.CurrencyRateUseCase
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import com.example.currencyexchangerates.ui.main.utils.DataFactory.Companion.getItemList
import com.example.currencyexchangerates.ui.main.utils.DataFactory.Companion.randomString
import com.example.currencyexchangerates.ui.main.utils.InstantExecutorExtension
import com.example.currencyexchangerates.ui.main.utils.LiveDataResult
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.*
import io.reactivex.disposables.Disposable
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class CurrencyRateViewModelTest {

    private val currencyRateUseCase: CurrencyRateUseCase = mock()
    private val resultObserver: Observer<LiveDataResult<MutableList<CurrencyListCellItem>>> = mock()

    private lateinit var currencyRateViewModel: CurrencyRateViewModel

    @BeforeEach
    fun setUp() {
        currencyRateViewModel = CurrencyRateViewModel(currencyRateUseCase)

        currencyRateViewModel.liveDataResult.observeForever(resultObserver)
    }

    @AfterEach
    fun tearDown() {
        reset(currencyRateUseCase)
    }

    @Test
    fun `should send loading LiveData and update list when currentList is empty`() {
        ArrangeBuilder().withUpdateDataSuccess()

        currencyRateViewModel.updateList()

        verify(resultObserver, times(1)).onChanged(LiveDataResult(LiveDataResult.Status.LOADING))
        verify(resultObserver, times(1)).onChanged(LiveDataResult(LiveDataResult.Status.SUCCESS, getItemList()))

        Truth.assertThat(currencyRateViewModel.liveDataResult.value?.data).isEqualTo(getItemList())
        Truth.assertThat(currencyRateViewModel.liveDataResult.value?.status).isEqualTo(LiveDataResult.Status.SUCCESS)
    }

    @Test
    fun `should NOT send loading LiveData and update list when currentList is NOT empty`() {
        ArrangeBuilder().withUpdateDataSuccess()

        currencyRateViewModel.updateBaseData(randomString(), randomString(), getItemList())
        currencyRateViewModel.updateList()

        verify(resultObserver, times(1)).onChanged(LiveDataResult(LiveDataResult.Status.SUCCESS, getItemList()))

        Truth.assertThat(currencyRateViewModel.liveDataResult.value?.data).isEqualTo(getItemList())
        Truth.assertThat(currencyRateViewModel.liveDataResult.value?.status).isEqualTo(LiveDataResult.Status.SUCCESS)
    }

    @Test
    fun `should send loading LiveData and throw error when currentList is empty`() {
        ArrangeBuilder().withUpdateDataError()

        currencyRateViewModel.updateList()

        verify(resultObserver, times(1)).onChanged(LiveDataResult(LiveDataResult.Status.LOADING))
        verify(resultObserver, times(1)).onChanged(LiveDataResult(LiveDataResult.Status.ERROR, message = ""))

        Truth.assertThat(currencyRateViewModel.liveDataResult.value?.message).isEqualTo("")
        Truth.assertThat(currencyRateViewModel.liveDataResult.value?.status).isEqualTo(LiveDataResult.Status.ERROR)
    }

    @Test
    fun `should NOT send loading LiveData and throw error when currentList is NOT empty`() {
        ArrangeBuilder().withUpdateDataError()

        currencyRateViewModel.updateBaseData(randomString(), randomString(), getItemList())
        currencyRateViewModel.updateList()

        verify(resultObserver, times(1)).onChanged(LiveDataResult(LiveDataResult.Status.ERROR, message = ""))

        Truth.assertThat(currencyRateViewModel.liveDataResult.value?.message).isEqualTo("")
        Truth.assertThat(currencyRateViewModel.liveDataResult.value?.status).isEqualTo(LiveDataResult.Status.ERROR)
    }

    @Test
    fun `should update list values`() {
        ArrangeBuilder().withUpdateValuesSuccess()

        currencyRateViewModel.updateBaseData(randomString(), randomString(), getItemList())
        currencyRateViewModel.updateValues(randomString())

        verify(resultObserver, times(1)).onChanged(LiveDataResult(LiveDataResult.Status.SUCCESS, getItemList()))

        Truth.assertThat(currencyRateViewModel.liveDataResult.value?.data).isEqualTo(getItemList())
        Truth.assertThat(currencyRateViewModel.liveDataResult.value?.status).isEqualTo(LiveDataResult.Status.SUCCESS)
    }

    inner class ArrangeBuilder {
        fun withUpdateDataSuccess(): ArrangeBuilder {
            val disposable: Disposable = mock()
            doAnswer {
                val listener = it.arguments[3] as CurrencyRateListener
                listener.onSuccess(getItemList())
                disposable
            }.whenever(currencyRateUseCase).updateData(any(), any(), any(), any())
            return this
        }

        fun withUpdateDataError(): ArrangeBuilder {
            val disposable: Disposable = mock()
            doAnswer {
                val listener = it.arguments[3] as CurrencyRateListener
                listener.onError(Throwable())
                disposable
            }.whenever(currencyRateUseCase).updateData(any(), any(), any(), any())
            return this
        }

        fun withUpdateValuesSuccess(): ArrangeBuilder {
            val disposable: Disposable = mock()
            doAnswer {
                val listener = it.arguments[2] as CurrencyRateListener
                listener.onSuccess(getItemList())
                disposable
            }.whenever(currencyRateUseCase).updateValues(any(), any(), any())
            return this
        }
    }
}