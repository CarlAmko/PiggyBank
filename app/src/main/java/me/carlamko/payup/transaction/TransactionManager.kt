package me.carlamko.payup.transaction

import android.content.Context
import android.preference.PreferenceManager
import android.widget.Toast
import me.carlamko.payup.model.DataChangeEvent
import me.carlamko.payup.model.ItemData
import me.carlamko.payup.model.UIEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionManager @Inject constructor(context: Context, private val items: List<ItemData>) {
    companion object {
        private fun generateKeyForItem(itemId: Int): String =
            "KEY_${itemId}_QUANTITY"

        private fun generateKeyForItem(itemData: ItemData): String = generateKeyForItem(itemData.id)
        private val savedState = hashMapOf<Int, Int>()
    }

    private val settings = PreferenceManager.getDefaultSharedPreferences(context)

    fun getItemQuantity(itemData: ItemData): Int =
        settings.getInt(generateKeyForItem(itemData), 0)

    fun incrementItem(itemData: ItemData) {
        setItemQuantity(itemData, getItemQuantity(itemData) + 1)
        EventBus.getDefault().post(UIEvent.ShowToast("Added ${itemData.name} x 1.", Toast.LENGTH_SHORT))
        EventBus.getDefault().post(DataChangeEvent)
    }

    fun decrementItem(itemData: ItemData) {
        val currentQuantity = getItemQuantity(itemData)
        // Only notify if value actually changed
        if (currentQuantity >= 1) {
            EventBus.getDefault().post(UIEvent.ShowToast("Removed ${itemData.name} x 1.", Toast.LENGTH_SHORT))
            EventBus.getDefault().post(DataChangeEvent)
        }
        // Quantity can't be negative
        setItemQuantity(itemData, Math.max(0, currentQuantity - 1))
    }

    fun getBalance(): Float = items.sumByDouble { getItemTotalValue(it).toDouble() }.toFloat()

    fun settleUp() {
        saveStateForUndo()
        items.forEach { resetItemTotal(it) }
        EventBus.getDefault().post(DataChangeEvent)
    }

    fun undo() {
        savedState.forEach { itemId, quantity -> setItemQuantity(itemId, quantity) }
        savedState.clear()
        EventBus.getDefault().post(DataChangeEvent)
    }

    private fun setItemQuantity(itemId: Int, quantity: Int) {
        settings.edit().apply {
            putInt(generateKeyForItem(itemId), quantity)
        }.apply()
    }

    private fun setItemQuantity(itemData: ItemData, quantity: Int) = setItemQuantity(itemData.id, quantity)

    private fun saveStateForUndo() {
        items.forEach { savedState[it.id] = getItemQuantity(it) }
    }

    private fun getItemTotalValue(itemData: ItemData): Float =
        getItemQuantity(itemData) * itemData.price

    private fun resetItemTotal(itemData: ItemData) {
        settings.edit().apply {
            putInt(generateKeyForItem(itemData), 0)
        }.apply()
    }
}