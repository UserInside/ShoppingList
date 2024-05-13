package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShoppingListRepository {

    suspend fun addShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    suspend fun editShoppingItem(shoppingItem: ShoppingItem)

    suspend fun getShoppingItem(shoppingItemId: Int): ShoppingItem

    fun getShoppingList(): LiveData<List<ShoppingItem>>
}