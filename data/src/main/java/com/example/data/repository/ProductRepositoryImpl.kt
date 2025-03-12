package com.example.data.repository

import android.util.Log
import com.example.data.firebase.FirebaseDao
import com.example.data.mapper.ProductMapper
import com.example.data.room.dao.ProductDao
import com.example.domain.model.ProductModel
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class ProductRepositoryImpl(
    private val firebaseDao: FirebaseDao,
    private val productDao: ProductDao,
    private val productMapper: ProductMapper

) : ProductRepository {

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

    override fun getProductList(): Flow<List<ProductModel>> =
        productDao.getProducts().map { list -> list.map { productMapper.toProductModel(it) } }

    override suspend fun syncProductFromFireBase(){
        firebaseDao.getProductFromFirebase()
       /* val productList  = firebaseDao.getProductFromFirebase()
        productList.forEach {
            Log.i("Sync","Sync "+ it)
            //productDao.insertOrUpdate(it)
        }
        val productFromRoom = productDao.getProducts().first()
        productFromRoom.forEach {
            if(!productList.contains(it)){
                productDao.deleteProduct(it)
            }
        }*/
    }
}