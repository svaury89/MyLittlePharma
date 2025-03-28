package com.example.data.mapper

import android.util.Log
import com.example.data.api.model.ProductApi
import com.example.data.api.model.ProductDetailAPi
import com.example.data.room.model.Product
import com.example.domain.extension.toDateWithFormat
import com.example.domain.extension.toMillis
import com.example.domain.model.ProductModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class ProductMapper {

    fun toProductModel(product: Product) =
        ProductModel(
            uid = product.uid,
            name = product.name ?: " ",
            description = product.description ?: "",
            date = Instant.ofEpochMilli(product.date).atZone(ZoneId.systemDefault()).toLocalDate(),
            image = product.image ?: ""
        )

    fun fromProductModel(productModel: ProductModel): Product {
        Log.i("ProductModel","ProductModel date to product " +productModel.date.toMillis())

        return Product(
            uid = productModel.uid,
            name = productModel.name,
            description = productModel.description,
            date = productModel.date.toMillis(),
            image = productModel.image
        )
    }

    fun productNetworktoProductModel(productDetailAPi: ProductDetailAPi): ProductModel =
        ProductModel(
            name = productDetailAPi.title ?: "",
            imageUrl = productDetailAPi.imageUrl,
            description = productDetailAPi.content ?: ""
        )

}