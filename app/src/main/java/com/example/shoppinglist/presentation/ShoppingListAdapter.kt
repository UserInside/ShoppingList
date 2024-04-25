package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingListAdapter : ListAdapter<ShoppingItem, ShoppingItemViewHolder>(ShoppingItemDiffCallback()) {

    var onShoppingItemLongClickListener: ((ShoppingItem) -> Unit)? = null
    var onShoppingItemClickListener: ((ShoppingItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.shopping_item_disabled
            VIEW_TYPE_ENABLED -> R.layout.shopping_item_enabled
            else -> throw RuntimeException("Unknown view type -> $viewType")
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)
        return ShoppingItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = getItem(position)
        holder.itemView.setOnLongClickListener {
            onShoppingItemLongClickListener?.invoke(shoppingItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShoppingItemClickListener?.invoke(shoppingItem)
        }

        holder.tvName.text = shoppingItem.name
        holder.tvAmount.text = shoppingItem.amount.toString()
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