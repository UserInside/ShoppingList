package com.example.shoppinglist.domain

class AddShoppingItemToListUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun addShoppingItemToList(shoppingItem: ShoppingItem) {
       shoppingListRepository.addShoppingItemToList(shoppingItem)
    }
}
