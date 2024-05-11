package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ShoppingItemDisabledBinding
import com.example.shoppinglist.databinding.ShoppingItemEnabledBinding
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingListAdapter :
    ListAdapter<ShoppingItem, ShoppingItemViewHolder>(ShoppingItemDiffCallback()) {

    var onShoppingItemLongClickListener: ((ShoppingItem) -> Unit)? = null
    var onShoppingItemClickListener: ((ShoppingItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.shopping_item_disabled
            VIEW_TYPE_ENABLED -> R.layout.shopping_item_enabled
            else -> throw RuntimeException("Unknown view type -> $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )

        return ShoppingItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnLongClickListener {
            onShoppingItemLongClickListener?.invoke(shoppingItem)
            true
        }
        binding.root.setOnClickListener {
            onShoppingItemClickListener?.invoke(shoppingItem)
        }

        when(binding){
            is ShoppingItemDisabledBinding -> {
                binding.tvName.text = shoppingItem.name
                binding.tvAmount.text = shoppingItem.amount.toString()
            }
            is ShoppingItemEnabledBinding -> {
                binding.tvName.text = shoppingItem.name
                binding.tvAmount.text = shoppingItem.amount.toString()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).isBought) {
            true -> VIEW_TYPE_DISABLED
            false -> VIEW_TYPE_ENABLED
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0

        const val MAX_POOL_SIZE = 15
    }
}