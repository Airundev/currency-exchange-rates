package com.example.currencyexchangerates.ui.components

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.ui.components.utils.CurrencyRateDiffCallback
import com.example.currencyexchangerates.ui.components.utils.DataBindingViewHolder
import kotlinx.android.synthetic.main.currency_list_cell.view.*
import java.util.*

class CurrencyListCellAdapter : RecyclerView.Adapter<DataBindingViewHolder>() {

    private var items: MutableList<CurrencyListCellItem> = mutableListOf()

    var baseItemCurrency: String? = null

    private var listener: CurrencyListCellListener? = null

    private var binding: ViewDataBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item = items[position]
        setOnClickListener(holder, position)
        setOnTextChanged(holder, position)
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.currency_list_cell
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: MutableList<CurrencyListCellItem>) {
        val diffResult = DiffUtil.calculateDiff(
            CurrencyRateDiffCallback(
                items,
                newItems
            )
        )
        this.items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    private fun setOnClickListener(holder: DataBindingViewHolder, position: Int) {
        holder.itemView.currencyListCellLayout.setOnClickListener {
            Collections.swap(items, position, 0)
            notifyItemMoved(position, 0)
            listener?.onCurrencyCellClicked()
            baseItemCurrency = items[position].title
            holder.itemView.currencyListCellEdit.requestFocus()
        }
    }

    private fun setOnTextChanged(holder: DataBindingViewHolder, position: Int) {
        holder.itemView.currencyListCellEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(exchange: Editable?) {

                if (position == 0 && exchange != null && exchange.toString().toDoubleOrNull() !=null) {

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(exchange: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    interface CurrencyListCellListener {
        fun onCurrencyCellClicked()
    }
}