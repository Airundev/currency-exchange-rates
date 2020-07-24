package com.example.currencyexchangerates.ui.components

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.databinding.CurrencyListCellBinding
import com.example.currencyexchangerates.ui.components.utils.CurrencyRateDiffCallback
import com.example.currencyexchangerates.ui.components.utils.CurrencyValueDifference
import com.example.currencyexchangerates.ui.components.utils.DataBindingViewHolder
import kotlinx.android.synthetic.main.currency_list_cell.view.*
import java.text.DecimalFormat
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
        val viewHolder = DataBindingViewHolder(binding!!)
        viewHolder.itemView.setOnClickListener { viewHolder.itemView.currencyListCellEdit.requestFocus() }
        viewHolder.itemView.currencyListCellEdit.apply { setOnFocusChangeListener { _, focused ->
            when(focused) {
                true -> {
                    onCurrencyClicked(viewHolder, viewHolder.adapterPosition)
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
            val df = DecimalFormat("0.00")
            item.currencyValue = df.format(baseValue.toDouble().times(item.currencyRate))
            if (!isFocused) {
                setText(item.currencyValue)
            }
        }
        holder.bind(item)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int, payloads: MutableList<Any>) {
        when(payloads.isEmpty()) {
            true -> onBindViewHolder(holder, position)
            false -> with(holder.itemView.currencyListCellEdit) {
                if (!isFocused) {
                    setText((payloads[0] as CurrencyValueDifference).newCurrencyValue)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.currency_list_cell
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long {
        return items[position].currencyCode.hashCode().toLong()
    }

    fun setItems(newItems: MutableList<CurrencyListCellItem>) {
        val diffResult = DiffUtil.calculateDiff(CurrencyRateDiffCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    private fun onCurrencyClicked(holder: DataBindingViewHolder, position: Int) {
        baseItemCurrency = items[position].currencyCode
        Collections.swap(items, position, 0)
        notifyItemMoved(position, 0)
        listener?.onCurrencyCellClicked()
        holder.itemView.currencyListCellEdit.requestFocus()
    }

    interface CurrencyListCellListener {
        fun onCurrencyCellClicked()
    }

    private val textWatcher: TextWatcher = object: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            baseValue = if (p0.toString() == "") { "0.00" } else { p0.toString() }
            for (item in items) {
                if (item.currencyCode != items[0].currencyCode) {
                    val df = DecimalFormat("0.00")
                    item.currencyValue = df.format(baseValue.toDouble().times(item.currencyRate))
                    notifyItemChanged(items.indexOf(item), CurrencyValueDifference(item.currencyValue))
                }
            }
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
}