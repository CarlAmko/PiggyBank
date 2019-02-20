package me.carlamko.payup

import dagger.Module
import dagger.Provides
import me.carlamko.payup.model.ItemData

@Module
class DataModule {
    @Provides
    fun providesItemData(): List<ItemData> =
        // @TODO un-hardcode this
        listOf(
            ItemData("Chocolate", R.drawable.chocolate, .5f),
            ItemData("Pastry", R.drawable.pastry, 1.5f),
            ItemData("Candy", R.drawable.candy, .5f),
            ItemData("Coffee", R.drawable.coffee, 1.5f)
        ).sortedBy { it.price }
}