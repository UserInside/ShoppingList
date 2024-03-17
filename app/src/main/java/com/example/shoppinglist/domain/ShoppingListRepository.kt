package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShoppingListRepository {

    fun addShoppingItemToList(shoppingItem: ShoppingItem)

    fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun editShoppingItem(shoppingItem: ShoppingItem)

    fun getShoppingItemById(shoppingItemId: Int): ShoppingItem

    fun getShoppingList(): LiveData<List<ShoppingItem>>
}