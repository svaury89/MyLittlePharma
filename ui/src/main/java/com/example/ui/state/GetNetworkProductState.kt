package com.example.ui.state

import com.example.ui.model.ProductUi

sealed class GetNetworkProductState {
    object isLoading : GetNetworkProductState()
    data class Product(val productUi: ProductUi) : GetNetworkProductState()
    data class Error(val message : String ? ) : GetNetworkProductState()
    object EmptyProduct : GetNetworkProductState()

}