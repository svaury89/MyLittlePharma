package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.ProductRepository
import com.example.ui.mapper.ProductUiMapper
import com.example.ui.state.GetProductsUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductListViewModel(
     val productRepository: ProductRepository,
    val mapper: ProductUiMapper
) : ViewModel() {

    val productState  = productRepository
        .getProductList()
        .map{
                products ->
            if(products.isEmpty()){
                productRepository
                GetProductsUiState.EmptyList
            }
            else { GetProductsUiState.isSuccess(products.map {mapper.toProductUi(it)}) }
        }.distinctUntilChanged()
        .stateIn(
            scope = viewModelScope ,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue =GetProductsUiState.isLoding
        )

    fun syncProducts(){
        viewModelScope.launch {
            productRepository.syncProductFromFireBase()
        }
    }
}