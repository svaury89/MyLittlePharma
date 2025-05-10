package com.example.ui.state

import com.example.ui.model.ProductUi


sealed class GetProductsUiState {
    object isLoding : GetProductsUiState()
    class isSuccess(val products : List<ProductUi>) : GetProductsUiState()
    object EmptyList : GetProductsUiState()
}