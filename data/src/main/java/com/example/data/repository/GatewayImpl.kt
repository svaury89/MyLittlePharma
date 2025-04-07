package com.example.data.repository

import com.example.data.remote.RemoteDatabaseHandler
import com.example.data.remote.dao.ProductRemoteSourceDao
import com.example.data.room.dao.LocalProductDao
import com.example.domain.repository.Gateway
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GatewayImpl(
    private val productRemoteSourceDao: ProductRemoteSourceDao,
    private val localProductDao: LocalProductDao,
    private val dispatcherIO: CoroutineDispatcher,
    private val externalScope: CoroutineScope = GlobalScope,

    ) : Gateway {

    override suspend fun syncProductFromFireBase() {
        withContext(dispatcherIO) {
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

    override suspend fun realTimeDataBase() {
        productRemoteSourceDao.updateRealTimeChangeFromRemoteDb(
        onAddOrUpdateChild = { localProduct ->
                externalScope.launch(dispatcherIO) {
                    localProductDao.insertOrUpdate(localProduct)
                }
            },

            onRemoveChild = { localProduct ->
                externalScope.launch(dispatcherIO) {
                    localProductDao.deleteProductIfExist(localProduct)
                }
            }
        )
    }
}