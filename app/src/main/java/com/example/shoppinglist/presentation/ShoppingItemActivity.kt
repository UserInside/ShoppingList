package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppinglist.R

class ShoppingItemActivity : AppCompatActivity() {
    lateinit var viewModel: ShoppingItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopping_item_enabled)
        viewModel.errorInputAmount.value
    }
}