package com.example.data.module

import android.app.Application
import androidx.room.Room
import com.example.data.room.dao.LocalProductDao
import org.koin.dsl.module
import com.example.data.room.MyLittlePharmaDB


fun provideDataBase(application: Application): MyLittlePharmaDB =
    Room.databaseBuilder(
        application,
        MyLittlePharmaDB::class.java,
        "table_product"
    ).fallbackToDestructiveMigration().build()

fun provideDao(myLittlePharmaDB: MyLittlePharmaDB): LocalProductDao = myLittlePharmaDB.productDao()


val dataBaseModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}
