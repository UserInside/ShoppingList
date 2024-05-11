package com.example.shoppinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.presentation.ShoppingItemFragment.Companion.newFragmentAddModeInstance
import com.example.shoppinglist.presentation.ShoppingItemFragment.Companion.newFragmentEditModeInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShoppingItemFragment.OnEditingFinished {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel
    private lateinit var shoppingListAdapter: ShoppingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()

        viewModel.shoppingList.observe(this) {
            shoppingListAdapter.submitList(it)
        }

        binding.btnAddShoppingItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShoppingItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(newFragmentAddModeInstance())
            }
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Saved successfully", Toast.LENGTH_LONG).show()
        supportFragmentManager.popBackStack()
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopping_item_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode(): Boolean {
        return binding.shoppingItemFragmentContainer == null
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
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

        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(binding.recyclerView)
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
            if (isOnePaneMode()) {
                val intent = ShoppingItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(newFragmentEditModeInstance(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        shoppingListAdapter.onShoppingItemLongClickListener = {
            viewModel.changeShoppingItemIsBought(it)
        }
    }
}