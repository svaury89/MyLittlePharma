package com.example.ui.composable

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.extension.toStringWithFormat
import com.example.domain.repository.ProductRepository
import com.example.ui.extension.registerOrDeleteAlarm
import com.example.ui.mapper.ProductUiMapper
import com.example.ui.model.ProductUi
import com.example.ui.validator.ProductUiValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

open class BaseFormeViewModel(
    open val validator : ProductUiValidator,
    open val productRepository: ProductRepository,
    open val mapper: ProductUiMapper
) : ViewModel() {

    internal val _productUi = MutableStateFlow(ProductUi(date = LocalDate.now().toStringWithFormat()))
    val productUi = _productUi.asStateFlow()

    internal val _validFormState = MutableStateFlow(false)
    val validFormState = _validFormState.asStateFlow()
    init {
        viewModelScope.launch {
            productUi.collect{
                _validFormState.value = validator?.validator(it) ?: false
            }
        }
    }

    fun saveProduct(context: Context) {
        val savedProductDraft = _productUi.value.copy()
        viewModelScope.launch {
            val productDraft = mapper.fromProductUi(savedProductDraft)
            productRepository.createOrUpdate(productDraft)
            productDraft.date.registerOrDeleteAlarm(context,productDraft.uid.hashCode(),true,productDraft.name)
            productDraft.date.registerOrDeleteAlarm(context,productDraft.uid.hashCode(), productName =  productDraft.name)
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

    @SuppressLint("NewApi")
    fun uriToBitmap(imageUri : Uri?, contentResolver: ContentResolver): android.graphics.Bitmap? =
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

    fun updateImage(bitMap : Bitmap){
        _productUi.update {
            it.copy(image = bitMap)
        }
    }
}