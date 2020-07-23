package com.example.currencyexchangerates.ui.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.databinding.CurrencyListCellBinding
import com.example.currencyexchangerates.ui.components.utils.DataBindingViewHolder
import kotlinx.android.synthetic.main.currency_list_cell.view.*
import java.util.*

class CurrencyListCellAdapter : RecyclerView.Adapter<DataBindingViewHolder>() {

    private var items: MutableList<CurrencyListCellItem> = mutableListOf()

    var baseItemCurrency: String = "EUR"
    var baseValue: String = "1.00"

    private var listener: CurrencyListCellListener? = null

    private var binding: CurrencyListCellBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item = items[position]
        setOnClickListener(holder, position)
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.currency_list_cell
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: MutableList<CurrencyListCellItem>) {
        /*val diffResult = DiffUtil.calculateDiff(
            CurrencyRateDiffCallback(
                items,
                newItems
            )
        )
        this.items = newItems
        diffResult.dispatchUpdatesTo(this)*/
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    private fun setOnClickListener(holder: DataBindingViewHolder, position: Int) {
        holder.itemView.currencyListCellLayout.setOnClickListener {
            baseItemCurrency = items[position].currencyCode
            Collections.swap(items, position, 0)
            notifyItemMoved(position, 0)
            listener?.onCurrencyCellClicked()
            holder.itemView.currencyListCellEdit.requestFocus()
        }
    }

    interface CurrencyListCellListener {
        fun onCurrencyCellClicked()
    }
}