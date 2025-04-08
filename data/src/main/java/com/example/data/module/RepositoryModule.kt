package com.example.data.module

import com.example.data.remote.dao.ProductRemoteSourceDao
import com.example.data.mapper.ProductMapper
import com.example.data.remote.RemoteDatabaseHandler
import com.example.data.repository.GatewayImpl
import com.example.data.repository.ProductRepositoryImpl
import com.example.domain.repository.Gateway
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import kotlin.math.sin

val repositoryModule = module {
    single { Dispatchers.IO }
    single { RemoteDatabaseHandler() }
    single { ProductRemoteSourceDao(get()) }
    single { ProductMapper() }
    single<ProductRepository> { ProductRepositoryImpl(get(), get(), get(), get(), get()) }
    single<Gateway> { GatewayImpl(
        productRemoteSourceDao = get(),
        localProductDao = get(),
        dispatcherIO = get(),
        mapper =  get()) }
}