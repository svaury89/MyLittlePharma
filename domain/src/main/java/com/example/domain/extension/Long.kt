package com.example.domain.extension

import java.time.Instant
import java.time.ZoneId


fun Long.toSpecificDateFormat(
    pattern: String = "dd/MM/yyyy"
): String {
    val localDate = this.toLocalDate()
    return localDate.toStringWithFormat(pattern)
}

fun Long.toLocalDate() =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
