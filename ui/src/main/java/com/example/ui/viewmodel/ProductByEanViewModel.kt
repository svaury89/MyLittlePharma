package com.example.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.extension.networkUrltoBitmap
import com.example.domain.model.Result
import com.example.domain.repository.ProductRepository
import com.example.ui.composable.ProductForm
import com.example.ui.mapper.ProductUiMapper
import com.example.ui.navigation.EanProductNavigation
import com.example.ui.state.GetNetworkProductState
import com.example.ui.validator.ProductUiValidator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProductByEanViewModel(
    override val validator : ProductUiValidator,
    override val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle,
    override val mapper: ProductUiMapper
) : ProductForm(validator = validator, productRepository = productRepository, mapper = mapper) {

    private val args = savedStateHandle.toRoute<EanProductNavigation>()

    val state  =
        productRepository.getProductByEan(ean = args.ean).map { result ->
            when (result) {
                is Result.Error -> GetNetworkProductState.Error(result.message)
                is Result.Success -> {
                    val productModel = result.productModel
                    if (productModel != null) {
                        _productUi.value  = mapper.toProductUi(
                                productModel
                            ).copy(image = productModel.imageUrl?.networkUrltoBitmap())
                            GetNetworkProductState.Product(
                                _productUi.value.copy()
                            )

                    } else {
                        GetNetworkProductState.EmptyProduct
                    }
                }
            }

        }.distinctUntilChanged()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = GetNetworkProductState.isLoading
            )

}