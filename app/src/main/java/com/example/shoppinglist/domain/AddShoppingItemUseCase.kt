package com.example.shoppinglist.domain

class AddShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun addShoppingItemToList(shoppingItem: ShoppingItem) {
       shoppingListRepository.addShoppingItem(shoppingItem)
    }
}
