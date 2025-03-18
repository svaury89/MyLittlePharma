package com.example.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ProductModel
import com.example.domain.repository.ProductRepository
import com.example.ui.state.GetProductsUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch

class ProductListViewModel(
     val productRepository: ProductRepository
) : ViewModel() {

    val productState  = productRepository
        .getProductList()
        .map{
                products ->
            if(products.isEmpty()){
                productRepository
                GetProductsUiState.EmptyList
            }
            else { GetProductsUiState.isSuccess(products) }
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