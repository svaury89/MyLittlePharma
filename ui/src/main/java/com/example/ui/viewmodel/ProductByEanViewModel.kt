package com.example.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.extension.networkUrltoBitmap
import com.example.domain.model.ApiResult
import com.example.domain.repository.ProductRepository
import com.example.ui.mapper.ProductUiMapper
import com.example.ui.navigation.EanProductNavigation
import com.example.ui.state.GetNetworkProductState
import com.example.ui.state.GetProductUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductByEanViewModel(
    val repository: ProductRepository,
    val productUiMapper: ProductUiMapper,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val args = savedStateHandle.toRoute<EanProductNavigation>()


    val state  =
        repository.getProductByEan(ean = args.ean).map { result ->
            when (result) {
                is ApiResult.Error -> GetNetworkProductState.Error(result.message)
                is ApiResult.Success -> {
                    val productModel = result.productModel
                    if (productModel != null) {
                        Log.i("ProductModel ","ProductModel "+ productModel.name + " "+ productModel.description)

                        val productUi = productUiMapper.toProductUi(
                                productModel
                            ).copy(image = productModel.imageUrl?.networkUrltoBitmap())
                            Log.i("ProductUi ","ProductUi "+ productUi.name + " "+ productUi.description)
                            GetNetworkProductState.Product(
                                productUi
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