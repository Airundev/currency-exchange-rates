package com.example.currencyexchangerates.ui.components.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangerates.BR
import com.example.currencyexchangerates.R

class DataBindingViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Any) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }

    companion object {
        @BindingAdapter("srcCompat")
        @JvmStatic
        fun setImage(imageView: ImageView, resourceId: Int?) {
            if (resourceId != null) {
                val drawable: Drawable? =
                    AppCompatResources.getDrawable(imageView.context, resourceId)
                imageView.setImageDrawable(drawable)
            } else {
                imageView.setImageResource(R.drawable.ic_unknown)
            }
        }
    }
}