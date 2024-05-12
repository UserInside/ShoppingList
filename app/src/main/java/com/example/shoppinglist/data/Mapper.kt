package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShoppingItem

class ShopListMapper {

    fun mapEntityToDbModel(shoppingItem: ShoppingItem): ShopItemDbModel {
        return ShopItemDbModel(
            id = shoppingItem.id,
            name = shoppingItem.name,
            amount = shoppingItem.amount,
            isBought = shoppingItem.isBought
        )
    }

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel): ShoppingItem {
        return ShoppingItem(
            id = shopItemDbModel.id,
            name = shopItemDbModel.name,
            amount = shopItemDbModel.amount,
            isBought = shopItemDbModel.isBought
        )
    }

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>): List<ShoppingItem> {
        return list.map{
            mapDbModelToEntity(it)
        }
    }
}