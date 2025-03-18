package com.example.ui.state

import com.example.domain.model.ProductModel

sealed class GetProductUiState {
    object isLoding :  GetProductUiState()
    class isSuccess(val product : ProductModel) : GetProductUiState()
}