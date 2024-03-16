package com.example.shoppinglist.domain

class DeleteShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListRepository.deleteShoppingItem(shoppingItem)

    }
}