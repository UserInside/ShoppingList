package com.example.shoppinglist.domain

class GetShoppingItemById(private val shoppingListRepository: ShoppingListRepository) {
    
    fun getShoppingItemById(id: Int): ShoppingItem {
        return shoppingListRepository.getShoppingItemById(id)
    }
}
