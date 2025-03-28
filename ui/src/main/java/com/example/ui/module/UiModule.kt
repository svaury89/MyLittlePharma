package com.example.ui.module

import com.example.ui.mapper.ProductUiMapper
import com.example.ui.viewmodel.AddOrUpdateProductViewModel
import com.example.ui.viewmodel.ProductByEanViewModel
import com.example.ui.viewmodel.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module{

    single {
        ProductUiMapper()
    }
    viewModel{
        ProductListViewModel(get(),get())
    }
    viewModel{
        AddOrUpdateProductViewModel(get(),get(),get())
    }
    viewModel{
        ProductByEanViewModel(get(),get(),get())
    }
}