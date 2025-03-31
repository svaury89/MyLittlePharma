package com.example.mylittlepharma.application

import android.app.Application
import com.example.data.module.dataBaseModule
import com.example.data.module.networkModule
import com.example.data.module.repositoryModule
import com.example.ui.module.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyLittlePharmaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyLittlePharmaApplication)
            modules(dataBaseModule, networkModule, repositoryModule, uiModule)
        }
    }
}