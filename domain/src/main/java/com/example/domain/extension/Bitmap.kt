package com.example.domain.extension

import android.graphics.Bitmap
import android.graphics.Matrix
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun Bitmap.convertToString(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encode(b)
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { setRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}