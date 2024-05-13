package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository

class ShoppingListRepositoryImpl(application: Application) : ShoppingListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override suspend fun addShoppingItem(shoppingItem: ShoppingItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shoppingItem))
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shopListDao.deleteShopItem(shoppingItem.id)
    }

    override suspend fun editShoppingItem(shoppingItem: ShoppingItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shoppingItem))
    }

    override suspend fun getShoppingItem(shoppingItemId: Int): ShoppingItem {
        val dbModel = shopListDao.getShopItem(shoppingItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShoppingList(): LiveData<List<ShoppingItem>> {
        return shopListDao.getShopList().map {
            mapper.mapListDbModelToListEntity(it)
        }
    }

}