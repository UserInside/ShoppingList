package com.example.shoppinglist.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInput")
fun bindErrorInput(til: TextInputLayout, isError: Boolean){
    til.error = when (isError) {
        true -> "Wrong input"
        false -> null
    }
}