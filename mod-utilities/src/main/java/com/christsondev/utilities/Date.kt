package com.christsondev.utilities

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

class Date(val timeInMillis: Long) {

    /**
     * @param separator any value e.g: slash `/` or comma `,` or dash `-`
     * @return dd<separator>MM<separator>yyyy e.g: dd-MM-yyyy or dd/MM/yyyy
     */
    fun toDayMonthYear(separator: String): String =
        if (timeInMillis > 0) {
            SimpleDateFormat("dd" + separator + "MM" + separator + "yyyy", Locale.getDefault()).format(Date(timeInMillis))
        } else {
            ""
        }

    /**
     * @return hh:mm a
     */
    fun toTime(): String =
        if (timeInMillis > 0) {
            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(timeInMillis))
        } else {
            ""
        }

    fun add(millis: Long) = Date(timeInMillis + millis)

    fun minus(millis: Long) = Date(timeInMillis - millis)

    fun isBefore(date: Date) = this.timeInMillis < date.timeInMillis

    fun isAfter(date: Date) = this.timeInMillis > date.timeInMillis

    companion object {
        fun now() = Date(Calendar.getInstance().timeInMillis)
    }
}