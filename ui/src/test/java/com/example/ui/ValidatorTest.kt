package com.example.ui

import com.example.domain.extension.toStringWithFormat
import com.example.ui.model.ProductUi
import com.example.ui.validator.ProductUiValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class ValidatorTest {

    val validator = ProductUiValidator()

    @Test
    fun validator_is_false_if_name_is_null_or_empty(){
        val productUi = ProductUi(name = "   ", description = "description", date = LocalDate.now().toStringWithFormat() )
        assertFalse(validator.validator(productUi))
    }

    @Test
    fun validator_is_false_if_description_is_null_or_empty(){
        val productUi = ProductUi(name = "name", description = "    ", date = LocalDate.now().toStringWithFormat() )
        assertFalse(validator.validator(productUi))
    }

    @Test
    fun validator_is_false_if_date_is_null_or_empty(){
        val productUi = ProductUi(name = "name", description = "description", date = "")
        assertFalse(validator.validator(productUi))
    }

    @Test
    fun validator_is_false_if_date_is_wrong_format_or_empty(){
        val productUi = ProductUi(name = "name", description = "description", date = "11-06-2023")
        assertFalse(validator.validator(productUi))
    }

    @Test
    fun validator_is_false_if_date_is_no_date_format_or_empty(){
        val productUi = ProductUi(name = "name", description = "description", date = "datewrong")
        assertFalse(validator.validator(productUi))
    }

    @Test
    fun validator_is_true(){
        val productUi = ProductUi(name = "name", description = "description", date = "11/03/2023")
        assertTrue(validator.validator(productUi))
    }
}