package com.example.ui

import com.example.domain.extension.toBarCodeAndDate
import org.junit.Test
import kotlin.test.assertEquals

class QrCodeMapperTest {

    @Test
    fun returnPairWhenQrCodeIsReceived(){

        val str = "001034009359558381727043010KX313"
        val result = str.toBarCodeAndDate()
        assertEquals("3400935955838",result.first)
        assertEquals("01/04/2027",result.second)
    }
}