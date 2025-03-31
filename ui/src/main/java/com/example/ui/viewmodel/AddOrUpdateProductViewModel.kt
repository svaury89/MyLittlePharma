package com.example.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.repository.ProductRepository
import com.example.ui.composable.ProductForm
import com.example.ui.mapper.ProductUiMapper
import com.example.ui.navigation.AddOrUpdateProductNavigation
import com.example.ui.state.GetProductUiState
import com.example.ui.validator.ProductUiValidator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class AddOrUpdateProductViewModel(
    override val validator : ProductUiValidator,
    override val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle,
    override val mapper: ProductUiMapper
) : ProductForm(validator = validator, productRepository = productRepository, mapper = mapper) {

    private val args = savedStateHandle.toRoute<AddOrUpdateProductNavigation>()


    val state = productRepository.getProductByIdOrNull(args.productUuid)
        .map { productModelResult ->
            productModelResult?.let {
                _productUi.value = mapper.toProductUi(productModelResult.copy())
                _validFormState.value = validator.validator(productUi.value)
            }
            GetProductUiState.isSuccess(_productUi.value.copy())
        }.distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GetProductUiState.isLoding
        )


}