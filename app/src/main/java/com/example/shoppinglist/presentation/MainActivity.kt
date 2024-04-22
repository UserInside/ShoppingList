package com.example.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shoppingListAdapter: ShoppingListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupRecyclerView()

        viewModel.shoppingList.observe(this) {
            shoppingListAdapter.submitList(it)
        }

        val btnAddItem = findViewById<FloatingActionButton>(R.id.btn_add_shopping_item)
        btnAddItem.setOnClickListener {
            val intent = ShoppingItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val rvShoppingList = findViewById<RecyclerView>(R.id.recycler_view)
        with(rvShoppingList) {
            shoppingListAdapter = ShoppingListAdapter()
            adapter = shoppingListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShoppingListAdapter.VIEW_TYPE_ENABLED,
                ShoppingListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShoppingListAdapter.VIEW_TYPE_DISABLED,
                ShoppingListAdapter.MAX_POOL_SIZE
            )
        }

        setupLongCkickListener()
        setupClickListener()
        setupSwipeListener(rvShoppingList)
    }

    private fun setupSwipeListener(rvShoppingList: RecyclerView) {
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shoppingListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShoppingItem(item)
            }

        }).attachToRecyclerView(rvShoppingList)
    }

    private fun setupClickListener() {
        shoppingListAdapter.onShoppingItemClickListener = {
            val intent = ShoppingItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongCkickListener() {
        shoppingListAdapter.onShoppingItemLongClickListener = {
            viewModel.changeShoppingItemIsBought(it)
        }
    }
}