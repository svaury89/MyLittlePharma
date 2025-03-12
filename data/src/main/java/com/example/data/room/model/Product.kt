package com.example.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date
import java.util.UUID

@Entity
data class Product(
    @PrimaryKey val uid: String =  UUID.randomUUID().toString(),
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "description") val description: String? =null,
    @ColumnInfo(name = "date") val date: Long = Date().time
)