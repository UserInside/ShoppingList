package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingListAdapter: RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    var shoppingList = listOf<ShoppingItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.shopping_item, parent, false)
        return ShoppingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.tvName.text = shoppingList[position].name
        holder.tvAmount.text = shoppingList[position].amount.toString()
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    class ShoppingListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvAmount = view.findViewById<TextView>(R.id.tv_amount)
    }
}