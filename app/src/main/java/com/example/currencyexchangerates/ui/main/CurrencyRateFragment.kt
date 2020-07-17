package com.example.currencyexchangerates.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.currencyexchangerates.R

class CurrencyRateFragment : Fragment() {

    companion object {
        fun newInstance() = CurrencyRateFragment()
    }

    private lateinit var viewModel: CurrencyRateViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrencyRateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}