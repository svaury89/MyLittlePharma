package com.example.ui.extension

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toStringWithFormat(pattern : String  ="dd/MM/yyyy") = this.format(DateTimeFormatter.ofPattern(pattern))
