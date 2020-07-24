package com.example.currencyexchangerates.ui.main

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
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
                result.data?.let { adapter.setItems(it) }
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

        adapter = CurrencyListCellAdapter()
        adapter.setHasStableIds(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        progressDialog = ProgressDialog(this)

        currencyRateViewModel.liveDataResult.observe(this, resultObserver)
        currencyRateViewModel.start(adapter.baseItemCurrency)
    }

    override fun onCurrencyCellClicked() {
        recyclerView.layoutManager?.scrollToPosition(0)
    }

    private fun onTimerTick() {
        currencyRateViewModel.updateList(adapter.baseItemCurrency)
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