package com.example.data.module

import android.app.Application
import androidx.room.Room
import com.example.data.room.dao.ProductDao
import org.koin.dsl.module
import com.example.data.room.MyLittlePharmaDB


fun provideDataBase(application: Application): MyLittlePharmaDB =
    Room.databaseBuilder(
        application,
        MyLittlePharmaDB::class.java,
        "table_product"
    ).fallbackToDestructiveMigration().build()

fun provideDao(myLittlePharmaDB: MyLittlePharmaDB): ProductDao = myLittlePharmaDB.productDao()


val dataBaseModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}
