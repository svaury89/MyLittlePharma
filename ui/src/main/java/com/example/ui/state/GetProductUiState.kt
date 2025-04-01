package com.example.ui.state

import com.example.ui.model.ProductUi

sealed class GetProductUiState {
    object isLoding :  GetProductUiState()
    class isSuccess(val product : ProductUi) : GetProductUiState()
}