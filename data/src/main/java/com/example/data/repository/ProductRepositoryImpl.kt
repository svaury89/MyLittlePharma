package com.example.data.repository

import com.example.data.firebase.FirebaseDao
import com.example.data.mapper.ProductMapper
import com.example.data.room.dao.ProductDao
import com.example.domain.model.ProductModel
import com.example.domain.repository.ProductReposirory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

internal class ProductRepositoryImpl(
    private val firebaseDao: FirebaseDao,
    private val productDao: ProductDao,
    private val productMapper: ProductMapper

) : ProductReposirory {

    override suspend fun addOrUpdateProduct(productModel: ProductModel): Boolean {
        val product = productMapper.fromProductModel(productModel)
        return runCatching {
            firebaseDao.writeProductOnFirebase(product)
        }.onSuccess {
            productDao.insertOrUpdate(product)

        }.fold(
            onSuccess = { true },
            onFailure = { false }
        )
    }

    override suspend fun getProductList(): Flow<List<ProductModel>> =
        productDao.getProducts().map { list -> list.map { productMapper.toProductModel(it) } }

    suspend fun syncProductFromFireBase(){
        val productList  = firebaseDao.getProductFromFirebase()
        productList.forEach {
            productDao.insertOrUpdate(it)
        }
        val productFromRoom = productDao.getProducts().first()
        productFromRoom.forEach {
            if(!productList.contains(it)){
                productDao.deleteProduct(it)
            }
        }
    }
}