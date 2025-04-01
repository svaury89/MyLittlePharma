package com.example.data.mapper

import com.example.data.api.model.ProductDetailAPi
import com.example.data.room.model.LocalProduct
import com.example.domain.extension.toLocalDate
import com.example.domain.extension.toMillis
import com.example.domain.model.ProductDraft
import java.time.LocalDate

class ProductMapper {

    fun toproduct(localProduct: LocalProduct) =
        com.example.domain.model.Product(
            uid = localProduct.uid,
            name = localProduct.name ?: " ",
            description = localProduct.description ?: "",
            date = localProduct.date.toLocalDate(),
            image = localProduct.image ?: "",
            imageUrl = null
        )

    fun fromProductDraft(productDraft: ProductDraft): LocalProduct {

        return LocalProduct(
            uid = productDraft.uid,
            name = productDraft.name,
            description = productDraft.description,
            date = productDraft.date.toMillis(),
            image = productDraft.image
        )
    }

    fun productNetworkToproduct(productDetailAPi: ProductDetailAPi): com.example.domain.model.Product =
        com.example.domain.model.Product(
            uid = "",
            name = productDetailAPi.title ?: "",
            imageUrl = productDetailAPi.imageUrl,
            description = productDetailAPi.content ?: "",
            image = null,
            date = LocalDate.now()
        )

}