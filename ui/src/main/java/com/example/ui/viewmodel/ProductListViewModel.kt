package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ProductModel
import com.example.domain.repository.ProductRepository
import com.example.ui.state.GetProductsUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class ProductListViewModel(
     val productRepository: ProductRepository
) : ViewModel() {

    val productState: StateFlow<GetProductsUiState>  = productRepository
        .getProductList()
        .transform<List<ProductModel>,GetProductsUiState> {
            products ->
            if(products.isEmpty()){
                productRepository
                GetProductsUiState.EmptyList
            }
            else { GetProductsUiState.isSuccess(products) }

        }
        .stateIn(
            scope = viewModelScope ,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue =GetProductsUiState.isLoding
        )

    fun saveProduct(productModel: ProductModel){
        viewModelScope.launch {
            productRepository.addOrUpdateProduct(productModel)
        }
    }

    fun syncProducts(){
        viewModelScope.launch {
            productRepository.syncProductFromFireBase()
        }
    }
}