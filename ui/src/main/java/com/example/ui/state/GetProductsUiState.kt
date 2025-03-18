package com.example.ui.state

import com.example.domain.model.ProductModel

sealed class GetProductsUiState {
    object isLoding : GetProductsUiState()
    class isSuccess(val products : List<ProductModel>) : GetProductsUiState()
    object EmptyList : GetProductsUiState()
}