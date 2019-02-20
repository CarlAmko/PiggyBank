package me.carlamko.payup.model

data class ItemData(val name: String, val iconRes: Int, val price: Float) {
    val id = name.hashCode()
}