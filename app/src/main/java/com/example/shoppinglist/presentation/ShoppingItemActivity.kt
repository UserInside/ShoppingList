package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShoppingItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShoppingItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var tilAmount: TextInputLayout
    private lateinit var etAmount: TextInputEditText
    private lateinit var btnSave: Button

    private var screenMode = MODE_UNKNOWN
    private var shoppingItemId = ShoppingItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_item)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShoppingItemViewModel::class.java]
        initViews()
        addTextChangedListeners()
        launchCorrectScreenMode()
        observeViewModel()
    }

    private fun launchCorrectScreenMode() {
        when (screenMode) {
            MODE_ADD -> launchAddModeScreen()
            MODE_EDIT -> launchEditModeScreen()
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(this) { input ->
            when (input) {
                true -> tilName.error = "Wrong name input"
                false -> tilName.error = null
            }
        }
        viewModel.errorInputAmount.observe(this) { input ->
            when (input) {
                true -> tilAmount.error = "Wrong amount input"
                false -> tilAmount.error = null
            }
        }

        viewModel.isReadyToClose.observe(this) {
            finish()
        }
    }

    private fun launchAddModeScreen() {
        btnSave.setOnClickListener {
            val inputName = etName.text?.toString()
            val inputAmount = etAmount.text?.toString()
            viewModel.addShoppingItem(inputName, inputAmount)
        }
    }

    private fun launchEditModeScreen() {
        viewModel.getShoppingItem(shoppingItemId)
        viewModel.shoppingItem.observe(this) {
            etName.setText(it.name)
            etAmount.setText(it.amount.toString())
        }

        btnSave.setOnClickListener {
            viewModel.editShoppingItem(
                etName.editableText?.toString(),
                etAmount.editableText?.toString()
            )
        }
    }

    private fun addTextChangedListeners() {
        etName.doOnTextChanged { _, _, _, _ ->
            viewModel.resetInputName()
        }
        etAmount.doOnTextChanged { _, _, _, _ ->
            viewModel.resetInputAmount()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_LAUNCH_MODE)) throw RuntimeException("ShoppingItemActivity launch mode not found")

        val launchMode = intent.getStringExtra(EXTRA_LAUNCH_MODE)
        if (launchMode != MODE_ADD && launchMode != MODE_EDIT) throw RuntimeException("Wrong ShoppingItemActivity launch mode - $launchMode")

        screenMode = launchMode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOPPING_ITEM_ID)) throw RuntimeException("ShoppingItem ID not found")
            shoppingItemId = intent.getIntExtra(EXTRA_SHOPPING_ITEM_ID, ShoppingItem.UNDEFINED_ID)
        }

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
        private const val EXTRA_SHOPPING_ITEM_ID = "extra_shopping_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

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