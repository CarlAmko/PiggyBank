package me.carlamko.payup.settings

import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Settings @Inject constructor(context: Context) {
    companion object {
        private const  val KEY_TOTAL = "KEY_TOTAL"
    }
    private val settings = PreferenceManager.getDefaultSharedPreferences(context)

    fun addToTotal(value: Float) {
        val currentTotal = settings.getFloat(KEY_TOTAL, 0f)
        settings.edit().apply {
            putFloat(KEY_TOTAL, currentTotal + value)
        }.apply()
    }

    fun resetTotal() {
        settings.edit().apply {
            putFloat(KEY_TOTAL, 0f)
        }.apply()
    }
}