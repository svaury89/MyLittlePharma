package com.example.data.repository

import com.example.data.remote.RemoteDatabaseHandler
import com.example.data.remote.dao.ProductRemoteSourceDao
import com.example.data.room.dao.LocalProductDao
import com.example.domain.repository.Gateway
import kotlinx.coroutines.flow.first

class GatewayImpl(
    private val productRemoteSourceDao: ProductRemoteSourceDao,
    private val localProductDao: LocalProductDao,
) : Gateway{

    override suspend fun syncProductFromFireBase() {
        val productList = productRemoteSourceDao.getProduct()
        productList.forEach {
            localProductDao.insertOrUpdate(it)
        }
        val productFromRoom = localProductDao.getProducts().first()
        productFromRoom.forEach {
            if (!productList.contains(it)) {
                localProductDao.deleteProductById(it.uid)
            }
        }
    }
}