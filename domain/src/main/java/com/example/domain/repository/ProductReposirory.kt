package com.example.domain.repository

import com.example.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductReposirory {

    suspend fun addOrUpdateProduct(productModel: ProductModel) : Boolean

    suspend fun getProductList():Flow<List<ProductModel>>

}