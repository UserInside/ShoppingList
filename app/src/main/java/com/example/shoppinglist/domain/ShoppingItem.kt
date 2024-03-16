package com.example.shoppinglist.domain

data class ShoppingItem(
    val name: String,
    val amount: Int,
    val isBought: Boolean,
    var id: Int = UNDEFINED_ID
) {

    companion object {

        const val UNDEFINED_ID = -1
    }
}
