package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository
import java.lang.RuntimeException

class ShoppingListRepositoryImpl : ShoppingListRepository {

    private val shoppingListLD = MutableLiveData<List<ShoppingItem>>()
    private val shoppingList = mutableListOf<ShoppingItem>()

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            addShoppingItemToList(ShoppingItem("Name $i", i, true))
        }
    }

    override fun addShoppingItemToList(shoppingItem: ShoppingItem) {
        if(shoppingItem.id == ShoppingItem.UNDEFINED_ID) {
            shoppingItem.id = autoIncrementId++
        }
        shoppingList.add(shoppingItem)
        updateList()
    }

    override fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingList.remove(shoppingItem)
        updateList()
    }

    override fun editShoppingItem(shoppingItem: ShoppingItem) {
        val oldItem = getShoppingItemById(shoppingItem.id)
        deleteShoppingItem(oldItem)
        addShoppingItemToList(shoppingItem)
    }

    override fun getShoppingItemById(shoppingItemId: Int): ShoppingItem {
        return shoppingListLD.value?.find { it.id == shoppingItemId }
            ?: throw RuntimeException("Item with id $shoppingItemId not found")
    }

    override fun getShoppingList(): LiveData<List<ShoppingItem>> {
        return shoppingListLD
    }

    fun updateList() {
        shoppingListLD.value = shoppingList.toList()
    }
}