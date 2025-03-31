package com.example.domain.extension

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDate.toStringWithFormat(pattern : String  ="dd/MM/yyyy") = this.format(DateTimeFormatter.ofPattern(pattern))

fun LocalDate.toMillis() = this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()