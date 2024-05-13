package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShoppingListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShoppingItemUseCase
import com.example.shoppinglist.domain.EditShoppingItemUseCase
import com.example.shoppinglist.domain.GetShoppingListUseCase
import com.example.shoppinglist.domain.ShoppingItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShoppingListRepositoryImpl(application)

    private val getShoppingListUseCase = GetShoppingListUseCase(repository)
    private val deleteShoppingItemUseCase = DeleteShoppingItemUseCase(repository)
    private val editShoppingItemUseCase = EditShoppingItemUseCase(repository)

    val shoppingList = getShoppingListUseCase.getShoppingList()

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            deleteShoppingItemUseCase.deleteShoppingItem(shoppingItem)
        }
    }

    fun changeShoppingItemIsBought(shoppingItem: ShoppingItem) {
        val newItem = shoppingItem.copy(isBought = !shoppingItem.isBought)
        viewModelScope.launch {
            editShoppingItemUseCase.editShoppingItem(newItem)
        }
    }

}