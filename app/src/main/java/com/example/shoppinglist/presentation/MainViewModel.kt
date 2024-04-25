package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShoppingListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShoppingItemUseCase
import com.example.shoppinglist.domain.EditShoppingItemUseCase
import com.example.shoppinglist.domain.GetShoppingListUseCase
import com.example.shoppinglist.domain.ShoppingItem

class MainViewModel : ViewModel() {

    private val repository = ShoppingListRepositoryImpl

    private val getShoppingListUseCase = GetShoppingListUseCase(repository)
    private val deleteShoppingItemUseCase = DeleteShoppingItemUseCase(repository)
    private val editShoppingItemUseCase = EditShoppingItemUseCase(repository)

    val shoppingList = getShoppingListUseCase.getShoppingList()

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        deleteShoppingItemUseCase.deleteShoppingItem(shoppingItem)
    }

    fun changeShoppingItemIsBought(shoppingItem: ShoppingItem) {
        val newItem = shoppingItem.copy(isBought = !shoppingItem.isBought)
        editShoppingItemUseCase.editShoppingItem(newItem)
    }
}