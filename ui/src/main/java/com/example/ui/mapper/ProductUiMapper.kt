package com.example.ui.mapper

import com.example.domain.extension.convertToString
import com.example.domain.extension.toBitMap
import com.example.domain.extension.toDateWithFormat
import com.example.domain.extension.toStringWithFormat
import com.example.domain.model.ProductDraft
import com.example.domain.model.Product
import com.example.ui.model.ProductUi

class ProductUiMapper {

    fun toProductUi(product: Product): ProductUi =
        ProductUi(
            uuid = product.uid,
            name = product.name,
            description = product.description,
            date = product.date.toStringWithFormat(),
            image = product.image?.toBitMap(),
        )


    fun fromProductUi(productUi: ProductUi): ProductDraft {
       return  ProductDraft(
            uid = productUi.uuid,
            name = productUi.name,
            description = productUi.description,
            date = productUi.date.toDateWithFormat(),
            image = productUi.image?.convertToString() ?: "",
        )
    }



}

