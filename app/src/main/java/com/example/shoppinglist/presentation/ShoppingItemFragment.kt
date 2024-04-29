package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShoppingItemFragment(
    private val screenMode: String = ShoppingItemActivity.MODE_UNKNOWN,
    private val shoppingItemId: Int = ShoppingItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ShoppingItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var tilAmount: TextInputLayout
    private lateinit var etAmount: TextInputEditText
    private lateinit var btnSave: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parseParams()
        viewModel = ViewModelProvider(this)[ShoppingItemViewModel::class.java]
        initViews(view)
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
        viewModel.errorInputName.observe(viewLifecycleOwner) { input ->
            when (input) {
                true -> tilName.error = "Wrong name input"
                false -> tilName.error = null
            }
        }
        viewModel.errorInputAmount.observe(viewLifecycleOwner) { input ->
            when (input) {
                true -> tilAmount.error = "Wrong amount input"
                false -> tilAmount.error = null
            }
        }

        viewModel.isReadyToClose.observe(viewLifecycleOwner) {
            activity?.onBackPressedDispatcher?.onBackPressed()
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
        viewModel.shoppingItem.observe(viewLifecycleOwner) {
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

    private fun parseParams() {
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) throw RuntimeException("ShoppingItemActivity launch mode not found")

    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        etName = view.findViewById(R.id.et_name)
        tilAmount = view.findViewById(R.id.til_amount)
        etAmount = view.findViewById(R.id.et_amount)
        btnSave = view.findViewById(R.id.btn_save)
    }

    companion object {

        private const val EXTRA_LAUNCH_MODE = "extra_launch_mode"
        private const val EXTRA_SHOPPING_ITEM_ID = "extra_shopping_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        const val MODE_UNKNOWN = ""

        fun newFragmentAddModeInstance() : ShoppingItemFragment {
            return ShoppingItemFragment(MODE_ADD)
        }

        fun newFragmentEditModeInstance(shoppingItemId: Int) : ShoppingItemFragment {
            return ShoppingItemFragment(MODE_EDIT, shoppingItemId)
        }

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
