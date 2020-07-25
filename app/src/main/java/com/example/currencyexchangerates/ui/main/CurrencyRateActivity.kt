package com.example.currencyexchangerates.ui.main

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.ui.components.CurrencyListCellAdapter
import com.example.currencyexchangerates.ui.components.CurrencyListCellItem
import com.example.currencyexchangerates.ui.main.utils.LifecycleAwareTimer
import com.example.currencyexchangerates.ui.main.utils.LiveDataResult
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel


class CurrencyRateActivity : AppCompatActivity(), CurrencyListCellAdapter.CurrencyListCellListener {

    private val currencyRateViewModel: CurrencyRateViewModel by viewModel()

    private lateinit var adapter: CurrencyListCellAdapter

    private lateinit var progressDialog: ProgressDialog

    private val timer: LifecycleAwareTimer by lazy {
        LifecycleAwareTimer(::onTimerTick)
    }

    private val resultObserver = Observer<LiveDataResult<MutableList<CurrencyListCellItem>>> { result ->
        when (result.status) {
            LiveDataResult.Status.SUCCESS -> {
                hideProgressDialog()
                result.data?.let { adapter.updateItems(it) }
                lifecycle.addObserver(timer)
            }
            LiveDataResult.Status.LOADING -> {
                showProgressDialog()
            }
            LiveDataResult.Status.ERROR -> {
                hideProgressDialog()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        adapter = CurrencyListCellAdapter(this)
        adapter.setHasStableIds(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        progressDialog = ProgressDialog(this)

        currencyRateViewModel.liveDataResult.observe(this, resultObserver)
        currencyRateViewModel.start()
    }

    override fun onCurrencyCellClicked(baseCurrency: String, baseValue: String) {
        currencyRateViewModel.baseCurrency = baseCurrency
        currencyRateViewModel.baseValue = baseValue
        recyclerView.layoutManager?.scrollToPosition(0)
    }

    override fun onBaseValueUpdated(baseValue: String) {
        currencyRateViewModel.baseValue = baseValue
        currencyRateViewModel.updateValues(adapter.items)
    }

    private fun onTimerTick() {
        currencyRateViewModel.updateList(adapter.items)
    }

    private fun showProgressDialog() {
        if (!progressDialog.isShowing) {
            progressDialog.setTitle(getString(R.string.loading))
            progressDialog.setMessage(getString(R.string.wait))
            progressDialog.show()
        }
    }

    private fun hideProgressDialog() {
        if (progressDialog.isShowing) progressDialog.dismiss()
    }
}