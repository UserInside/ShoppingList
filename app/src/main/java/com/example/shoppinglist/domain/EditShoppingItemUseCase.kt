package com.example.shoppinglist.domain

class EditShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun editShoppingItem(shoppingItem: ShoppingItem){
        shoppingListRepository.editShoppingItem(shoppingItem)
    }
}