package com.example.cryptocurrencyapp

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import android.app.Application
import com.example.cryptocurrencyapp.di.appModule
import com.example.cryptocurrencyapp.di.networkModule

open class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(listOf(appModule, networkModule))
        }
    }
}