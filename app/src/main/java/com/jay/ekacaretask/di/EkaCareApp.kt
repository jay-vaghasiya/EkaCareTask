package com.jay.ekacaretask.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class EkaCareApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EkaCareApp)
            androidLogger(Level.DEBUG)
            modules(module)
        }
    }
}