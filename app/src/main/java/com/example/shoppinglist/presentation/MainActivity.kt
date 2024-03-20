package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        adapter = ShoppingListAdapter()
        rvShoppingList.adapter = adapter
    }
}