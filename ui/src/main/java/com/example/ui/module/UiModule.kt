package com.example.ui.module

import com.example.ui.viewmodel.AddOrUpdateProductViewModel
import com.example.ui.viewmodel.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module{

    viewModel{
        ProductListViewModel(get())
    }
    viewModel{
        AddOrUpdateProductViewModel(get(),get())
    }
}