package com.example.data.repository

import android.util.Log
import com.example.data.firebase.FirebaseDao
import com.example.data.mapper.ProductMapper
import com.example.data.room.dao.ProductDao
import com.example.data.room.model.Product
import com.example.domain.model.ProductModel
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
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

    override fun getProductByIdOrNull(id : String?): Flow<ProductModel?> =
        if(id != null) {
            productDao.getProductById(id).map { productMapper.toProductModel(it) }
        }else{
            flowOf(null)
        }


    //ToTest
    override suspend fun syncProductFromFireBase(){
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