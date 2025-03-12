package com.example.data.mapper

import com.example.data.room.model.Product
import com.example.domain.model.ProductModel
import java.util.Date

class ProductMapper {

    fun toProductModel(product: Product) =
        ProductModel(
            uid = product.uid,
            name = product.name,
            description = product.description,
            date = Date(product.date)
        )

    fun fromProductModel(productModel: ProductModel) =
        Product(
            uid = productModel.uid,
            name = productModel.name,
            description = productModel.description,
            date = productModel.date.time
        )
}