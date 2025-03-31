package com.example.ui.mapper

import android.content.Context
import android.util.Log
import com.example.domain.model.ProductModel
import com.example.domain.extension.convertToString
import com.example.domain.extension.networkUrltoBitmap
import com.example.domain.extension.toBitMap
import com.example.domain.extension.toDateWithFormat
import com.example.domain.extension.toStringWithFormat
import com.example.ui.model.ProductUi

class ProductUiMapper {

    fun toProductUi(productModel: ProductModel): ProductUi =
        ProductUi(
            uuid = productModel.uid,
            name = productModel.name,
            description = productModel.description,
            date = productModel.date.toStringWithFormat(),
            image = productModel.image?.toBitMap(),
        )


    fun fromProductUi(productUi: ProductUi): ProductModel  {
       return  ProductModel(
            uid = productUi.uuid,
            name = productUi.name,
            description = productUi.description,
            date = productUi.date.toDateWithFormat(),
            image = productUi.image?.convertToString() ?: "",
        )
    }



}

