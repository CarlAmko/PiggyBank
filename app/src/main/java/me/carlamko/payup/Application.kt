package me.carlamko.payup

import android.app.Application

class Application : Application() {

    companion object {
        lateinit var injector: Component
            private set
    }

    override fun onCreate() {
        super.onCreate()

        // build dependency graph
        injector = DaggerComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

}