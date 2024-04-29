package com.example.shoppinglist.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShoppingItemFragment : Fragment() {

    private lateinit var viewModel: ShoppingItemViewModel
    private lateinit var onEditingFinished: OnEditingFinished

    private lateinit var tilName: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var tilAmount: TextInputLayout
    private lateinit var etAmount: TextInputEditText
    private lateinit var btnSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var shoppingItemId: Int = ShoppingItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinished) {
            onEditingFinished = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinished interface")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            onEditingFinished.onEditingFinished()
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
        val args = requireArguments()
        if (!args.containsKey(LAUNCH_MODE))
            throw RuntimeException("Launch mode not found")

        val mode = args.getString(LAUNCH_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT)
            throw RuntimeException("ShoppingItemActivity launch mode not found -> $mode <-")

        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOPPING_ITEM_ID)) {
                throw RuntimeException("Shopping item not found")
            }
            shoppingItemId = args.getInt(SHOPPING_ITEM_ID, ShoppingItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        etName = view.findViewById(R.id.et_name)
        tilAmount = view.findViewById(R.id.til_amount)
        etAmount = view.findViewById(R.id.et_amount)
        btnSave = view.findViewById(R.id.btn_save)
    }

    interface OnEditingFinished{
        fun onEditingFinished()
    }

    companion object {

        private const val LAUNCH_MODE = "extra_launch_mode"
        private const val SHOPPING_ITEM_ID = "extra_shopping_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        const val MODE_UNKNOWN = ""

        fun newFragmentAddModeInstance(): ShoppingItemFragment {
            return ShoppingItemFragment().apply {
                arguments = Bundle().apply {
                    putString(LAUNCH_MODE, MODE_ADD)
                }
            }
        }

        fun newFragmentEditModeInstance(shoppingItemId: Int): ShoppingItemFragment {
            return ShoppingItemFragment().apply {
                arguments = Bundle().apply {
                    putString(LAUNCH_MODE, MODE_EDIT)
                    putInt(SHOPPING_ITEM_ID, shoppingItemId)
                }
            }
        }
    }
}
