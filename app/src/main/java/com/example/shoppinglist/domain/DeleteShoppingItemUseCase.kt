package com.example.shoppinglist.domain

class DeleteShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListRepository.deleteShoppingItem(shoppingItem)

    }
}