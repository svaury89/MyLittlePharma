package com.example.ui.mapper

import com.example.domain.model.ProductModel
import com.example.ui.extension.toDateWithFormat
import com.example.ui.extension.toStringWithFormat
import com.example.ui.model.ProductUi

class ProductUiMapper {

    fun toProductUi(productModel: ProductModel): ProductUi =
        ProductUi(
            uuid = productModel.uid,
            name = productModel.name,
            description = productModel.description,
            date = productModel.date.toStringWithFormat()
        )


    fun fromProductUi(productUi: ProductUi): ProductModel =
        ProductModel(
            uid = productUi.uuid,
            name = productUi.name,
            description = productUi.description,
            date = productUi.date.toDateWithFormat()
        )



}