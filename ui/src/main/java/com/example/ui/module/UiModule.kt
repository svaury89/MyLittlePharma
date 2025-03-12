package com.example.ui.module

import com.example.ui.viewmodel.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module{
    viewModelOf(::ProductListViewModel)
}