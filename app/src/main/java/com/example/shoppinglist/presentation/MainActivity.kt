package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R


class MainActivity : AppCompatActivity() {

    private var count = true
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shoppingList.observe(this) {
            Log.i("MATest", it.toString())
            if (count == true) {
                val item = it[0]
                count = false
                viewModel.changeShoppingItemIsBought(item)

            }

        }


    }
}