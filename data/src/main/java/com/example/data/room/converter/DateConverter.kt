package com.example.data.room.converter

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun longToDate(longDate: Long?) = longDate?.let { Date(it) }


    @TypeConverter
    fun dateToLong(date: Date?) = date?.time
}