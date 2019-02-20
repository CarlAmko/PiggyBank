package me.carlamko.payup

import dagger.Component
import me.carlamko.payup.model.ItemData
import me.carlamko.payup.transaction.TransactionManager
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, DataModule::class])
interface Component {
    fun transactionManager(): TransactionManager
    fun itemData(): List<ItemData>
}