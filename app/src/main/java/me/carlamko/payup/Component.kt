package me.carlamko.payup

import dagger.Component
import me.carlamko.payup.settings.Settings
import me.carlamko.payup.transaction.TransactionManager
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class])
interface Component {
    fun settings(): Settings
    fun transactionManager(): TransactionManager
}