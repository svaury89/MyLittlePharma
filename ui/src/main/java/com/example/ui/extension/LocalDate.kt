package com.example.ui.extension

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.domain.extension.toMillis
import com.example.ui.broadcast.AlarmReceiver
import java.time.LocalDate

fun LocalDate.registerOrDeleteAlarm(context : Context, id: Int, isDeleted: Boolean = true){
    val alarmManager =
        context.getSystemService(AlarmManager :: class.java)
    val intent = Intent(context, AlarmReceiver::class.java)
    val flag = if (isDeleted) PendingIntent.FLAG_CANCEL_CURRENT else  PendingIntent.FLAG_IMMUTABLE
    val pendingIntent =
        PendingIntent.getBroadcast(context,id, intent, flag or PendingIntent.FLAG_IMMUTABLE)
    if(isDeleted) {
        alarmManager?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,  this.toMillis(), pendingIntent)
    }else{
        alarmManager?.cancel(pendingIntent)
    }

}

