package com.example.mylittlepharma.application

import android.app.Application
import com.example.data.module.dataBaseModule
import com.example.data.module.repositoryModule
import org.koin.core.context.startKoin

class MyLittlePharmaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(dataBaseModule, repositoryModule)
        }
    }
}