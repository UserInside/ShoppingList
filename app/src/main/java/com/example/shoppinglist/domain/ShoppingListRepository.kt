package com.example.shoppinglist.domain

interface ShoppingListRepository {

    fun addShoppingItemToList(shoppingItem: ShoppingItem)

    fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun editShoppingItem(shoppingItem: ShoppingItem)

    fun getShoppingItemById(shoppingItemId: Int): ShoppingItem

    fun getShoppingList(): List<ShoppingItem>
}