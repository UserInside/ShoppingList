package com.example.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShoppingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupRecyclerView()

        viewModel.shoppingList.observe(this) {
            adapter.shoppingList = it

        }


    }

    private fun setupRecyclerView() {
        val rvShoppingList = findViewById<RecyclerView>(R.id.recycler_view)
        with(rvShoppingList) {
            adapter = ShoppingListAdapter()
            recycledViewPool.setMaxRecycledViews(
                ShoppingListAdapter.VIEW_TYPE_ENABLED,
                ShoppingListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShoppingListAdapter.VIEW_TYPE_DISABLED,
                ShoppingListAdapter.MAX_POOL_SIZE
            )
        }
    }
}