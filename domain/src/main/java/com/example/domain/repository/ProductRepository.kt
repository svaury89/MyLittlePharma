package com.example.domain.repository

import com.example.domain.model.ApiResult
import com.example.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun addOrUpdateProduct(productModel: ProductModel) : Boolean

    fun getProductList():Flow<List<ProductModel>>

    fun getProductByIdOrNull(id : String?) : Flow<ProductModel?>

    suspend fun syncProductFromFireBase()

    fun getProductByEan(ean: String) : Flow<ApiResult>
}