package com.example.data.module

import com.example.data.remote.dao.ProductRemoteSourceDao
import com.example.data.mapper.ProductMapper
import com.example.data.remote.RemoteDatabaseHandler
import com.example.data.repository.GatewayImpl
import com.example.data.repository.ProductRepositoryImpl
import com.example.domain.repository.Gateway
import com.example.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RemoteDatabaseHandler()}
    single { ProductRemoteSourceDao(get()) }
    single {ProductMapper() }
    single <ProductRepository> { ProductRepositoryImpl(get(),get(),get(),get()) }
    single <Gateway> { GatewayImpl(get(),get()) }

}