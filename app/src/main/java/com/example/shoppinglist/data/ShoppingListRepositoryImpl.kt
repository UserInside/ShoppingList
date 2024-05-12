package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository
import java.lang.RuntimeException
import kotlin.random.Random

class ShoppingListRepositoryImpl(application: Application) : ShoppingListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun addShoppingItem(shoppingItem: ShoppingItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shoppingItem))
    }

    override fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shopListDao.deleteShopItem(shoppingItem.id)
    }

    override fun editShoppingItem(shoppingItem: ShoppingItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shoppingItem))
    }

    override fun getShoppingItem(shoppingItemId: Int): ShoppingItem {
        val dbModel = shopListDao.getShopItem(shoppingItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShoppingList(): LiveData<List<ShoppingItem>> {
        return shopListDao.getShopList().map {
            mapper.mapListDbModelToListEntity(it)
        }
    }

}