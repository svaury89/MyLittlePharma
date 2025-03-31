package com.example.ui.validator

import com.example.domain.extension.isDateFormatValid
import com.example.domain.extension.isNullOrEmpty
import com.example.ui.model.ProductUi

class ProductUiValidator {

    fun validator(productUi: ProductUi) : Boolean {
       val valuesAreNotEmpty =  (productUi.name.isNullOrEmpty() or productUi.description.isNullOrEmpty() or productUi.date.isNullOrEmpty()).not()
        return valuesAreNotEmpty and productUi.date.isDateFormatValid()
    }
}