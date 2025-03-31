package com.example.ui

import androidx.compose.material3.DatePicker
import com.example.domain.extension.toDateWithFormat
import com.example.domain.extension.toStringWithFormat
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.util.Date

class DateFormatUnitTest {

    @Test
    fun map_date_to_string_with_specific_format() {
        val date = LocalDate.of(2018, 7, 1)
        assertEquals("01/07/2018", date.toStringWithFormat())
    }

    @Test
    fun map_string_to_date_with_other_format_string_with_specific_format() {
        val string = "01-04-2025"
        assertEquals(LocalDate.now(), string.toDateWithFormat())
    }

    @Test
    fun map_string_to_date_with_other_date_format() {
        val string = "01/04/2025"
        assertEquals(LocalDate.of(2025, 4, 1), string.toDateWithFormat())
    }
}