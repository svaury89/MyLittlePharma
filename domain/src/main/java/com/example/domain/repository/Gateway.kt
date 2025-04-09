package com.example.domain.repository

import com.example.domain.model.Product
import java.time.LocalDate

interface Gateway {
    suspend fun syncProductFromFireBase(
        registerAlarm : (Product)-> Unit,
        removeAlarm : (Product) -> Unit
    )

    suspend fun realTimeDataBase()
}