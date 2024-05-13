package com.example.shoppinglist.domain

class AddShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    suspend fun addShoppingItemToList(shoppingItem: ShoppingItem) {
       shoppingListRepository.addShoppingItem(shoppingItem)
    }
}
