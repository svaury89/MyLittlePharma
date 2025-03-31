package com.example.domain.extension

import android.content.Context
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

fun String?.isNullOrEmpty() = this.isNullOrBlank() or (this != null && this.isEmpty())


fun String.isDateFormatValid(pattern: String = "dd/MM/yyyy"): Boolean {
    try {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val localDate = LocalDate.parse(this, formatter)
        return formatter.format(localDate).equals(this)
    } catch (e: Exception) {
        return false
    }
}


