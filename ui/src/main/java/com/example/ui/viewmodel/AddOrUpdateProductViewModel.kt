package com.example.ui.viewmodel


import android.annotation.SuppressLint
import android.content.ContentResolver
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.repository.ProductRepository
import com.example.ui.mapper.ProductUiMapper
import com.example.ui.model.ProductUi
import com.example.ui.navigation.AddOrUpdateProductNavigation
import com.example.ui.state.GetProductUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AddOrUpdateProductViewModel(
    val productRepository: ProductRepository,
    private val savedStateHandle: SavedStateHandle,
    val mapper: ProductUiMapper
) : ViewModel() {

    val args = savedStateHandle.toRoute<AddOrUpdateProductNavigation>()

    private val _productUi = MutableStateFlow(ProductUi())
    val productUi = _productUi.asStateFlow()

    val state = productRepository.getProductByIdOrNull(args.productUuid)
        .map { productModelResult ->
            productModelResult?.let {
                _productUi.value = mapper.toProductUi(productModelResult.copy())
            }
            GetProductUiState.isSuccess(_productUi.value)
        }.distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GetProductUiState.isLoding
        )

    fun saveProduct() {
        val savedProductModel = _productUi.value.copy()
        viewModelScope.launch {
            productRepository.addOrUpdateProduct(mapper.fromProductUi(savedProductModel))
        }
    }


    @SuppressLint("NewApi")
    fun uriToBitmap(imageUri : Uri ?, contentResolver: ContentResolver): android.graphics.Bitmap? =
        if(imageUri != null){
            val source = ImageDecoder.createSource(contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        } else null

    fun updateImage(uri: Uri?, contentResolver: ContentResolver){
        uri?.let {
            _productUi.update {
                it.copy(image = uriToBitmap(uri,contentResolver))
            }
        }
    }




    fun updateProductUi(name: String? = null, description: String? = null, date: String? = null) {
        _productUi.update {
            it.copy(
                name = name ?: _productUi.value.name,
                description = description ?: _productUi.value.description,
                date =  date ?: _productUi.value.date
            )
        }

    }

}