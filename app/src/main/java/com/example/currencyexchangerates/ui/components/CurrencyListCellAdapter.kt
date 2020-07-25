package com.example.currencyexchangerates.ui.components

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.databinding.CurrencyListCellBinding
import com.example.currencyexchangerates.ui.components.utils.DataBindingViewHolder
import kotlinx.android.synthetic.main.currency_list_cell.view.*

class CurrencyListCellAdapter(private var listener: CurrencyListCellListener)
    : RecyclerView.Adapter<DataBindingViewHolder>() {

    var items: MutableList<CurrencyListCellItem> = mutableListOf()

    private var binding: CurrencyListCellBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        val viewHolder = DataBindingViewHolder(binding!!)
        viewHolder.itemView.setOnClickListener { viewHolder.itemView.currencyListCellEdit.requestFocus() }
        viewHolder.itemView.currencyListCellEdit.apply { setOnFocusChangeListener { _, focused ->
            when(focused) {
                true -> {
                    onCurrencyClicked(viewHolder.adapterPosition)
                    addTextChangedListener(textWatcher)
                }
                false -> removeTextChangedListener(textWatcher)
            }}
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item = items[position]
        with(holder.itemView.currencyListCellEdit) {
            if (!isFocused) {
                setText(item.currencyValue)
            }
        }
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.currency_list_cell
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long {
        return items[position].currencyCode.hashCode().toLong()
    }

    fun updateItems(newItems: MutableList<CurrencyListCellItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun onCurrencyClicked(position: Int) {
        val item = items[position]
        items.remove(item)
        items.add(0, item)
        notifyItemMoved(position, 0)
        listener.onCurrencyCellClicked(items[position].currencyCode, items[position].currencyValue)
    }

    interface CurrencyListCellListener {
        fun onCurrencyCellClicked(baseCurrency: String, baseValue: String)
        fun onBaseValueUpdated(baseValue: String)
    }

    private val textWatcher: TextWatcher = object: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            val baseValue = if (p0.toString() == "") { "0.00" } else { p0.toString() }
            listener.onBaseValueUpdated(baseValue)
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
}