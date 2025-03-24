package com.example.data.mapper

import com.example.data.room.model.Product
import com.example.domain.model.ProductModel
import java.time.Instant
import java.time.ZoneId

class ProductMapper {

    fun toProductModel(product: Product) =
        ProductModel(
            uid = product.uid,
            name = product.name ?: " ",
            description = product.description ?:"",
            date = Instant.ofEpochMilli(product.date).atZone(ZoneId.systemDefault()).toLocalDate(),
            image = product.image ?: ""
        )

    fun fromProductModel(productModel: ProductModel) =
        Product(
            uid = productModel.uid,
            name = productModel.name,
            description = productModel.description,
            date = productModel.date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(),
            image = productModel.image
        )
}