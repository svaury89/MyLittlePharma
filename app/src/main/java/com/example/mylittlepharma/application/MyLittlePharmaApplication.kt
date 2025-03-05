package com.example.mylittlepharma.application

import android.app.Application
import org.koin.core.context.startKoin
import room.module.dataBaseModule

class MyLittlePharmaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(dataBaseModule)
        }
    }
}