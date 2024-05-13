package com.example.shoppinglist.domain

class EditShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    suspend fun editShoppingItem(shoppingItem: ShoppingItem){
        shoppingListRepository.editShoppingItem(shoppingItem)
    }
}