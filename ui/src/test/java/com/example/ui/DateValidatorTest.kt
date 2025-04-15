package com.example.ui

import com.example.domain.extension.reformatIfInputIsDate
import org.junit.Test
import kotlin.test.assertEquals

class DateValidatorTest {

    @Test
    fun date_with_format_is_valid(){
        val str = "01-04-2000"
        assertEquals("01/04/2000",str.reformatIfInputIsDate())
    }

    @Test
    fun date_with_spaces_is_valid(){
        val str = "01 04 2000"
        assertEquals("01/04/2000",str.reformatIfInputIsDate())
    }

    @Test
    fun date_with_no_day_and_spaces_is_valid(){
        val str = "04 2000"
        assertEquals("01/04/2000",str.reformatIfInputIsDate())
    }

    @Test
    fun date_with_no_day_and_format_is_valid(){
        val str = "04-2000"
        assertEquals("01/04/2000",str.reformatIfInputIsDate())
    }

    @Test
    fun date_with_point_is_valid(){
        val str = "01.04.2000"
        assertEquals("01/04/2000",str.reformatIfInputIsDate())
    }

    @Test
    fun date_with_no_day_and_point_is_valid(){
        val str = "04.2000"
        assertEquals("01/04/2000",str.reformatIfInputIsDate())
    }

    @Test
    fun date_with_spaces_before_and_after_is_valid(){
        val str = "   04.2000  "
        assertEquals("01/04/2000",str.reformatIfInputIsDate())
    }

    @Test
    fun date_with_spaces_between_days_and_spaces_before_and_after_is_valid(){
        val str = "   04 2000   "
        assertEquals("01/04/2000",str.reformatIfInputIsDate())
    }
    
    @Test
    fun string_with_letter_is_not_valid(){
        val str = "01/04/Test"
        assertEquals(null,str.reformatIfInputIsDate())
    }
}