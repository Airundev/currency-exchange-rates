package com.example.currencyexchangerates.ui.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.ui.components.utils.DataBindingViewHolder

class CurrencyListCellAdapter : RecyclerView.Adapter<DataBindingViewHolder>() {

    private val items: MutableList<CurrencyListCellItem> = mutableListOf()

    private var listener: CurrencyListCellListener? = null

    private var binding: ViewDataBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, listener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.currency_list_cell
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<CurrencyListCellItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun onCellClicked(item: CurrencyListCellItem) {
        items.remove(item)
        items.add(0, item)
        notifyDataSetChanged()
    }

    interface CurrencyListCellListener {
        fun onCurrencyCellClicked(item: CurrencyListCellItem)
    }
}