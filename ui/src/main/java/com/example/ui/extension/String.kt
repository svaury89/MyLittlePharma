package com.example.ui.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


fun String.toDateWithFormat(pattern : String = "dd/MM/yyyy"): LocalDate =
    try {
        LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))
    }catch (e: DateTimeParseException){
        LocalDate.now()
    }

@OptIn(ExperimentalEncodingApi::class)
fun String.toBitMap() : Bitmap ? =
    if(this.isNotEmpty()){
        val imageBytes = Base64.decode(this, 0)
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }else null



