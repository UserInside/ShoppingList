package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository
import java.lang.RuntimeException

class ShoppingListRepositoryImpl : ShoppingListRepository {

    private val shoppingList = mutableListOf<ShoppingItem>()

    private var autoIncrementId = 0

    override fun addShoppingItemToList(shoppingItem: ShoppingItem) {
        if(shoppingItem.id == ShoppingItem.UNDEFINED_ID) {
            shoppingItem.id = autoIncrementId++
        }
        shoppingList.add(shoppingItem)
    }

    override fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingList.remove(shoppingItem)
    }

    override fun editShoppingItem(shoppingItem: ShoppingItem) {
        val oldItem = getShoppingItemById(shoppingItem.id)
        deleteShoppingItem(oldItem)
        addShoppingItemToList(shoppingItem)
    }

    override fun getShoppingItemById(shoppingItemId: Int): ShoppingItem {
        return shoppingList.find { it.id == shoppingItemId }
            ?: throw RuntimeException("Item with id $shoppingItemId not found")
    }

    override fun getShoppingList(): List<ShoppingItem> {
        return shoppingList.toList()
    }
}