package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShoppingListRepositoryImpl
import com.example.shoppinglist.domain.AddShoppingItemUseCase
import com.example.shoppinglist.domain.EditShoppingItemUseCase
import com.example.shoppinglist.domain.GetShoppingItemUseCase
import com.example.shoppinglist.domain.ShoppingItem
import kotlinx.coroutines.launch

class ShoppingItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShoppingListRepositoryImpl(application)

    private val addShoppingItemUseCase = AddShoppingItemUseCase(repository)
    private val editShoppingItemUseCase = EditShoppingItemUseCase(repository)
    private val getShoppingItemUseCase = GetShoppingItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputAmount = MutableLiveData<Boolean>()
    val errorInputAmount: LiveData<Boolean>
        get() = _errorInputAmount

    private val _shoppingItem = MutableLiveData<ShoppingItem>()
    val shoppingItem: LiveData<ShoppingItem>
        get() = _shoppingItem

    private val _isReadyToClose = MutableLiveData<Unit>()
    val isReadyToClose: LiveData<Unit>
        get() = _isReadyToClose


    fun addShoppingItem(inputName: String?, inputAmount: String?) {
        val name = parseName(inputName)
        val amount = parseAmount(inputAmount)
        if (validateInput(name, amount)) {
            val newItem = ShoppingItem(name = name, amount = amount, isBought = false)
            viewModelScope.launch {
                addShoppingItemUseCase.addShoppingItemToList(newItem)
                finishWork()
            }
        }
    }

    fun editShoppingItem(inputName: String?, inputAmount: String?) {
        val name = parseName(inputName)
        val amount = parseAmount(inputAmount)
        if (validateInput(name, amount)) {
            _shoppingItem.value?.let {
                val item = it.copy(name = name, amount = amount)
                viewModelScope.launch {
                    editShoppingItemUseCase.editShoppingItem(item)
                    finishWork()
                }
            }
        }
    }

    fun getShoppingItem(id: Int) {
        viewModelScope.launch {
            _shoppingItem.postValue(getShoppingItemUseCase.getShoppingItem(id))
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseAmount(inputAmount: String?): Int {
        return try {
            inputAmount?.trim()?.toInt() ?: 0
        } catch (e: IllegalArgumentException) {
            0
        }
    }

    private fun validateInput(name: String, amount: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (amount <= 0) {
            _errorInputAmount.value = true
            result = false
        }
        return result
    }

    fun resetInputName() {
        _errorInputName.value = false
    }

    fun resetInputAmount() {
        _errorInputAmount.value = false
    }

    private fun finishWork() {
        _isReadyToClose.postValue(Unit)
    }

}