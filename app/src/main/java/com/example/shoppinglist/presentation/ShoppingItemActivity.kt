package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingItemActivity : AppCompatActivity() {

    private var screenMode = MODE_UNKNOWN
    private var shoppingItemId = ShoppingItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_item)
        parseIntent()
        if(savedInstanceState == null) {
            launchCorrectScreenMode()
        }
    }

    private fun launchCorrectScreenMode() {
        val fragment = when (screenMode) {
            MODE_ADD -> ShoppingItemFragment.newFragmentAddModeInstance()
            MODE_EDIT -> ShoppingItemFragment.newFragmentEditModeInstance(shoppingItemId)
            else -> throw RuntimeException("Wrong ShoppingItemActivity screen mode - $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopping_item_fragment_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_LAUNCH_MODE)) throw RuntimeException("ShoppingItemActivity launch mode not found")

        val launchMode = intent.getStringExtra(EXTRA_LAUNCH_MODE)
        if (launchMode != MODE_ADD && launchMode != MODE_EDIT) throw RuntimeException("Wrong ShoppingItemActivity launch mode - $launchMode")

        screenMode = launchMode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOPPING_ITEM_ID)) throw RuntimeException("ShoppingItem ID not found")
            shoppingItemId = intent.getIntExtra(EXTRA_SHOPPING_ITEM_ID, ShoppingItem.UNDEFINED_ID)
        }

    }

    companion object {

        private const val EXTRA_LAUNCH_MODE = "extra_launch_mode"
        private const val EXTRA_SHOPPING_ITEM_ID = "extra_shopping_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShoppingItemActivity::class.java)
            intent.putExtra(EXTRA_LAUNCH_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shoppingItemId: Int): Intent {
            val intent = Intent(context, ShoppingItemActivity::class.java)
            intent.putExtra(EXTRA_LAUNCH_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOPPING_ITEM_ID, shoppingItemId)
            return intent
        }


    }
}