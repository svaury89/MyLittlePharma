package com.example.domain.repository

import com.example.domain.model.GetProductBy
import com.example.domain.model.ProductDraft
import com.example.domain.model.Product
import com.example.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun createOrUpdate(productDraft: ProductDraft) : Boolean

    fun getProductList():Flow<List<Product>>

    fun getProduct(getProductBy : GetProductBy) : Flow<Result>
}