package com.example.data.repository

import com.example.data.api.service.GetProductApiService
import com.example.data.firebase.FirebaseDao
import com.example.data.mapper.ProductMapper
import com.example.data.room.dao.ProductDao
import com.example.domain.model.Result
import com.example.domain.model.ProductModel
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class ProductRepositoryImpl(
    private val firebaseDao: FirebaseDao,
    private val productDao: ProductDao,
    private val productMapper: ProductMapper,
    private val getProductApiService: GetProductApiService

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

    override fun getProductByIdOrNull(id: String?): Flow<ProductModel?> =
        if (id != null) {
            productDao.getProductById(id).map { productMapper.toProductModel(it) }
        } else {
            flowOf(null)
        }


    //ToTest
    override suspend fun syncProductFromFireBase() {
        val productList = firebaseDao.getProductFromFirebase()
        productList.forEach {
            productDao.insertOrUpdate(it)
        }
        val productFromRoom = productDao.getProducts().first()
        productFromRoom.forEach {
            if (!productList.contains(it)) {
                productDao.deleteProduct(it)
            }
        }
    }


    override fun getProductByEan(ean: String): Flow<Result> = flow {
        emit(
            try {
                val productApi = getProductApiService.getProductApiByEan(ean = ean)

                val errors = productApi.errors
                if(errors?.isNotEmpty() == true){
                    Result.Error(errors[0].errorMessage?.message)
                }else {
                    Result.Success(
                        if (productApi.product != null) {
                            productMapper.productNetworktoProductModel(productApi.product)
                        } else {
                            null
                        }
                    )
                }
            } catch (e: Exception) {
                Result.Error(e.message)
            }
        )
    }
}