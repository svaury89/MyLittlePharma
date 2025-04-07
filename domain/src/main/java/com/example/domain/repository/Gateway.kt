package com.example.domain.repository

interface Gateway {
    suspend fun syncProductFromFireBase()

    suspend fun realTimeDataBase()
}