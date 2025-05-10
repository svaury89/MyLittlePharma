package com.example.ui.validator

import com.example.domain.extension.isDateFormatValid
import com.example.ui.model.ProductUi

class ProductUiValidator {

    fun validator(productUi: ProductUi) : Boolean {
       val valuesAreNotEmpty =  (productUi.name.isNullOrBlank() or productUi.description.isNullOrBlank() or productUi.date.isNullOrBlank()).not()
        return valuesAreNotEmpty and productUi.date.isDateFormatValid()
    }
}