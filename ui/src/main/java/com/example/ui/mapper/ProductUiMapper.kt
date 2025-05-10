package com.example.ui.mapper

import com.example.domain.extension.convertToString
import com.example.domain.extension.toBitMap
import com.example.domain.extension.toDateWithFormat
import com.example.domain.extension.toStringWithFormat
import com.example.domain.model.ProductDraft
import com.example.domain.model.Product
import com.example.ui.model.DateState
import com.example.ui.model.ProductUi
import java.time.LocalDate
import java.util.UUID

class ProductUiMapper {

    fun toProductUi(product: Product): ProductUi =
        ProductUi(
            uuid = product.uid ,
            name = product.name,
            description = product.description,
            date = product.date.toStringWithFormat(),
            image = product.image?.toBitMap(),
            dateState =  dateStateRules(product.date)
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

    private fun dateStateRules (date : LocalDate) = if(date.isBefore(LocalDate.now())){
        DateState.EXPIRED
    }else if(date.minusDays(3).isBefore(LocalDate.now())){
        DateState.EXPIREDSOON
    }else{
        DateState.VALID
    }



}

