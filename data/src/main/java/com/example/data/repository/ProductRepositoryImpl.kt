package com.example.data.repository

import com.example.data.api.service.GetProductApiService
import com.example.data.remote.dao.ProductRemoteSourceDao
import com.example.data.mapper.ProductMapper
import com.example.data.room.dao.LocalProductDao
import com.example.domain.model.GetProductBy
import com.example.domain.model.ProductDraft
import com.example.domain.model.Product
import com.example.domain.model.Result
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class ProductRepositoryImpl(
    private val productRemoteSourceDao: ProductRemoteSourceDao,
    private val localProductDao: LocalProductDao,
    private val productMapper: ProductMapper,
    private val getProductApiService: GetProductApiService

) : ProductRepository {

    override suspend fun createOrUpdate(productDraft: ProductDraft): Boolean {
        val product = productMapper.fromProductDraft(productDraft)
        return runCatching {
            productRemoteSourceDao.writeProduct(product)
        }.onSuccess {
            localProductDao.insertOrUpdate(product)

        }.fold(
            onSuccess = { true },
            onFailure = { false }
        )
    }

    override fun getProductList(): Flow<List<Product>> =
        localProductDao.getProducts().map { list -> list.map { productMapper.toproduct(it) } }



    private fun getProductByIdOrNull(id: String?): Flow<Product?> =
        if (id != null) {
            localProductDao.getProductById(id).map { productMapper.toproduct(it) }
        } else {
            flowOf(null)
        }


    private fun getProductByEan(ean: String): Flow<Result> = flow {
        emit(
            try {
                val productApi = getProductApiService.getProductApiByEan(ean = ean)

                val errors = productApi.errors
                if(errors?.isNotEmpty() == true){
                    Result.Error(errors[0].errorMessage?.message)
                }else {
                    Result.Success(
                        if (productApi.product != null) {
                            productMapper.productNetworkToproduct(productApi.product)
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

    override fun getProduct(getProductBy: GetProductBy):Flow<Result> =
        when(getProductBy){
            is GetProductBy.Ean -> getProductByEan(getProductBy.ean)
            is GetProductBy.ProductId -> getProductByIdOrNull(getProductBy.id).map { Result.Success(it)}
        }

}