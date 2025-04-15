package com.example.domain.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


fun String.toDateWithFormat(pattern: String = "dd/MM/yyyy"): LocalDate =
    try {
        LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))
    } catch (e: DateTimeParseException) {
        LocalDate.now()
    }

@OptIn(ExperimentalEncodingApi::class)
fun String.toBitMap(): Bitmap? =
    if (this.isNotEmpty()) {
        val imageBytes = Base64.decode(this, 0)
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    } else null


suspend fun String.networkUrltoBitmap(): Bitmap? {

    val url = this
    return try {
        withContext(Dispatchers.IO) {
            BitmapFactory.decodeStream(
                URL(url).openConnection().getInputStream()
            )
        }
    } catch (e: IOException) {
        null
    }
}


fun String.isDateFormatValid(pattern: String = "dd/MM/yyyy"): Boolean {
    try {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val localDate = LocalDate.parse(this, formatter)
        return formatter.format(localDate).equals(this)
    } catch (e: Exception) {
        return false
    }
}

fun String.reformatIfInputIsDate(): String ? {


    val dateToTest = this.trim().replace("-","/").replace(" ","/").replace(".","/")
    //"dd/MM/yyyy"
    val regex1 = "^(3[01]|[12][0-9]|0[1-9]|[1-9])/(1[0-2]|0[1-9]|[1-9])/[0-9]{4}$".toRegex()

    //MM/yyyy"
    val regex2 = "(1[0-2]|0[1-9]|[1-9])/[0-9]{4}$".toRegex()

    //"yyyy/MM"
    val regex3 =
        "([0-9]{4})/(1[0-2]|0[1-9]|[1-9])$".toRegex()

    //"yyyy/MM/dd"
    val regex4 =
        "([0-9]{4})/(1[0-2]|0[1-9]|[1-9])/(3[01]|[12][0-9]|0[1-9]|[1-9])$".toRegex()

    val bool1: Boolean = regex1.matches(dateToTest)
    val bool2: Boolean = regex2.matches(dateToTest)
    val bool3 : Boolean = regex3.matches(dateToTest)
    val bool4: Boolean = regex4.matches(dateToTest)

    return when{
        bool1 || bool4 -> dateToTest
        bool3 -> dateToTest + "/01"
        bool2-> "01/" + dateToTest
        else -> null
    }


}


