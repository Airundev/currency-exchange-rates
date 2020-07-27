package com.example.currencyexchangerates.ui.components

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.ui.components.utils.DataBindingViewHolder
import kotlinx.android.synthetic.main.currency_list_cell.view.*

class CurrencyListCellAdapter(private var onBaseCurrencyClicked: (String, String, MutableList<CurrencyListCellItem>) -> Unit,
                              private var onBaseValueUpdated: (String) -> Unit)
    : RecyclerView.Adapter<DataBindingViewHolder>() {

    private var items: MutableList<CurrencyListCellItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = DataBindingViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false))
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

    override fun getItemViewType(position: Int): Int = R.layout.currency_list_cell
    override fun getItemCount(): Int = items.size
    override fun getItemId(position: Int): Long = items[position].currencyCode.hashCode().toLong()

    fun updateItems(newItems: MutableList<CurrencyListCellItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun onCurrencyClicked(position: Int) {
        val item = items[position]
        /*This two lines are just so offline mode works properly.
        It doesn't have any real impact on the online mode
        since in that case the rate will update on it's own via the API call.*/
        items[0].currencyRate = item.currencyRate
        item.currencyRate = 1.0
        /*Do this instead of Collections.swap() so the item that was first on the list
        falls on second place, instead of being swapped to the selected item's old position */
        items.remove(item)
        items.add(0, item)
        notifyItemMoved(position, 0)
        onBaseCurrencyClicked(item.currencyCode, item.currencyValue, items)
    }

    private val textWatcher: TextWatcher = object: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            val baseValue = if (p0.toString() == "") "0.00" else p0.toString()
            onBaseValueUpdated(baseValue)
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
}