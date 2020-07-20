package com.example.currencyexchangerates.ui.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.ui.components.utils.DataBindingViewHolder

class CurrencyListCellAdapter : RecyclerView.Adapter<DataBindingViewHolder>() {

    private val items: MutableList<CurrencyListCellItem> = mutableListOf()

    var listener: CurrencyListCellListener? = null

    protected var binding: ViewDataBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item = getItemForPosition(position)
        holder.bind(item, getListener())
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutId()
    }

    override fun getItemCount(): Int = items.size

    private fun getItemForPosition(position: Int): Any = items[position]

    private fun getListener(): Any? = listener

    private fun getLayoutId(): Int = R.layout.currency_list_cell

    fun setItems(newItems: List<CurrencyListCellItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    interface CurrencyListCellListener {
        fun onCurrencyCellClicked(item: CurrencyListCellItem)
    }
}