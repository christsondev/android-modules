package com.christsondev.utilities

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Date(val millis: Long = Calendar.getInstance().timeInMillis) {

    fun toDayMonthYearDash(): String =
        if (millis > 0) {
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(millis))
        } else {
            ""
        }

    fun toDayMonthYearSlash(): String =
        if (millis > 0) {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(millis))
        } else {
            ""
        }

    fun toTime(): String =
        if (millis > 0) {
            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(millis))
        } else {
            ""
        }
}