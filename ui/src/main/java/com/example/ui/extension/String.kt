package com.example.ui.extension

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


fun String.toDateWithFormat(pattern : String = "dd/MM/yyyy"): LocalDate =
    try {
        LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))
    }catch (e: DateTimeParseException){
        LocalDate.now()
    }

