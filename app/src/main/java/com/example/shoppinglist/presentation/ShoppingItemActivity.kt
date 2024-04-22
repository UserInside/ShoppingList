package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShoppingItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var etName: TextInputLayout
    private lateinit var tilAmount: TextInputEditText
    private lateinit var etAmount: TextInputEditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_item)
        initViews()
        viewModel = ViewModelProvider(this)[ShoppingItemViewModel::class.java]

    }

    private fun parseIntent() {
        //todo if (intent) and launchmodes
    }

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        etName = findViewById(R.id.et_name)
        tilAmount = findViewById(R.id.til_amount)
        etAmount = findViewById(R.id.et_amount)
        btnSave = findViewById(R.id.btn_save)
    }

    companion object {

        private const val EXTRA_LAUNCH_MODE = "extra_launch_mode"
        private const val EXTRA_SHOPPING_ITEM_ID = "extra_shopping_item_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShoppingItemActivity::class.java)
            intent.putExtra(EXTRA_LAUNCH_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shoppingItemId: Int): Intent {
            val intent = Intent(context, ShoppingItemActivity::class.java)
            intent.putExtra(EXTRA_LAUNCH_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOPPING_ITEM_ID, shoppingItemId)
            return intent
        }


    }
}