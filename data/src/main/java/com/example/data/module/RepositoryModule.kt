package com.example.data.module

import com.example.data.firebase.FirebaseDao
import com.example.data.mapper.ProductMapper
import com.example.data.repository.ProductRepositoryImpl
import com.example.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { FirebaseDao() }
    single {ProductMapper() }
    single <ProductRepository> { ProductRepositoryImpl(get(),get(),get(),get()) }
}