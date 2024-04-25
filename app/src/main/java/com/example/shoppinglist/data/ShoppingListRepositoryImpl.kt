package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShoppingListRepositoryImpl : ShoppingListRepository {

    private val shoppingListLD = MutableLiveData<List<ShoppingItem>>()
    private val shoppingList = sortedSetOf<ShoppingItem>({o1, o2 -> o1.id.compareTo(o2.id)})

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            addShoppingItem(ShoppingItem("Name $i", i, Random.nextBoolean()))
        }
    }

    override fun addShoppingItem(shoppingItem: ShoppingItem) {
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
        val oldItem = getShoppingItem(shoppingItem.id)
        shoppingList.remove(oldItem)
        addShoppingItem(shoppingItem)
    }

    override fun getShoppingItem(shoppingItemId: Int): ShoppingItem {
        return shoppingList.find { it.id == shoppingItemId }
            ?: throw RuntimeException("Item with id $shoppingItemId not found")
    }

    override fun getShoppingList(): LiveData<List<ShoppingItem>> {
        return shoppingListLD
    }

    private fun updateList() {
        shoppingListLD.value = shoppingList.toList()
    }
}