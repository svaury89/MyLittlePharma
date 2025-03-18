package com.example.ui.viewmodel


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.model.ProductModel
import com.example.domain.repository.ProductRepository
import com.example.ui.navigation.AddOrUpdateProductNavigation
import com.example.ui.state.GetProductUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddOrUpdateProductViewModel(
    val productRepository: ProductRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val args = savedStateHandle.toRoute<AddOrUpdateProductNavigation>()

    var productModel = ProductModel()

    val state = productRepository.getProductByIdOrNull(args.productUuid)
        .map { productModelResult ->
            productModelResult?.let {
                productModel = productModelResult.copy()
            }
            GetProductUiState.isSuccess(productModel)
        }.distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GetProductUiState.isLoding
        )

    fun saveProduct(name: String, description: String) {
       val savedProductModel =  productModel.copy(name = name, description = description)
        viewModelScope.launch {
            productRepository.addOrUpdateProduct(savedProductModel)
        }
    }
}