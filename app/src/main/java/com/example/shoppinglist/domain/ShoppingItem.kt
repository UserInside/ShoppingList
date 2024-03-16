package com.example.shoppinglist.domain

data class ShoppingItem(
    val name: String,
    val amount: Int,
    val id: Int,
    val isBought: Boolean,
)
