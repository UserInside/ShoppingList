package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingListAdapter : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    var shoppingList = listOf<ShoppingItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var count = 0

    var onShoppingItemLongClickListener: ((ShoppingItem) -> Unit)? = null
    var onShoppingItemClickListener: ((ShoppingItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {

        Log.d("SHOPListAdapter", "onCreateViewHolder ${++count}")
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.shopping_item_disabled
            VIEW_TYPE_ENABLED -> R.layout.shopping_item_enabled
            else -> throw RuntimeException("Unknown view type -> $viewType")
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)
        return ShoppingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val shoppingItem = shoppingList[position]
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

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    class ShoppingListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvAmount = view.findViewById<TextView>(R.id.tv_amount)
    }

    override fun getItemViewType(position: Int): Int {
        return when (shoppingList[position].isBought) {
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