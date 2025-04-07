package com.example.data.remote.dao

import com.example.data.room.model.LocalProduct

interface ProductRemoteSource {
    fun writeProduct(localProduct: LocalProduct)
    suspend fun getProduct() : List<LocalProduct>
    fun deleteProductById(id: String)
    suspend fun updateRealTimeChangeFromRemoteDb(
        onRemoveChild :  (LocalProduct) -> Unit,
        onAddOrUpdateChild : (LocalProduct) -> Unit
    )
}