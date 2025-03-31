package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.room.converter.DateConverter
import com.example.data.room.dao.ProductDao
import com.example.data.room.model.Product

@Database(entities = [Product::class], version = 2)
@TypeConverters(DateConverter :: class)
abstract class MyLittlePharmaDB :  RoomDatabase() {

    abstract fun productDao() : ProductDao
}