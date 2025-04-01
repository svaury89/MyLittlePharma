package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.room.converter.DateConverter
import com.example.data.room.dao.LocalProductDao
import com.example.data.room.model.LocalProduct

@Database(entities = [LocalProduct::class], version = 3)
@TypeConverters(DateConverter :: class)
abstract class MyLittlePharmaDB :  RoomDatabase() {

    abstract fun productDao() : LocalProductDao
}