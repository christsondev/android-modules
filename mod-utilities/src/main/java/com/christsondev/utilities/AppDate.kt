package com.christsondev.utilities

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppDate(val timeInMillis: Long) {

    fun format(format: DateFormat, locale: Locale = Locale.getDefault()) =
        if (timeInMillis > 0) {
            SimpleDateFormat(format.get(), locale).format(Date(timeInMillis))
        } else {
            ""
        }

    fun add(millis: Long) = AppDate(timeInMillis + millis)

    fun minus(millis: Long) = AppDate(timeInMillis - millis)

    fun isBefore(date: AppDate) = this.timeInMillis < date.timeInMillis

    fun isAfter(date: AppDate) = this.timeInMillis > date.timeInMillis

    companion object {
        fun now() = AppDate(Calendar.getInstance().timeInMillis)
    }

    sealed interface DateFormat {
        fun get(): String

        data class NumericMonth(val separator: String) : DateFormat {
            override fun get() = "dd" + separator + "MM" + separator + "yyyy"
        }

        data class TextMonth(val separator: String) : DateFormat {
            override fun get() = "dd" + separator + "MMM" + separator + "yyyy"
        }

        data object StandardTime : DateFormat {
            override fun get() = "hh:mm a"
        }
    }
}