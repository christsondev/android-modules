package com.christsondev.utilities

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AppDate(val timeInMillis: Long) {

    fun format(format: Formatter, locale: Locale = Locale.getDefault()) =
        if (timeInMillis > 0) {
            SimpleDateFormat(format.get(), locale).format(Date(timeInMillis))
        } else {
            ""
        }

    fun add(millis: Long) = AppDate(timeInMillis + millis)

    fun minus(millis: Long) = AppDate(timeInMillis - millis)

    fun toCalendar() = Calendar.getInstance().apply { timeInMillis = this@AppDate.timeInMillis }

    private fun isBefore(date: AppDate, unit: TimeUnit = TimeUnit.MILLIS): Boolean {
        return when (unit) {
            TimeUnit.MILLIS -> this.timeInMillis < date.timeInMillis
            TimeUnit.SECONDS -> (this.timeInMillis / 1000) < (date.timeInMillis / 1000)
            TimeUnit.MONTHS -> {
                val currentCalender = this.toCalendar()
                val otherCalendar = date.toCalendar()

                val thisYear = currentCalender.get(Calendar.YEAR)
                val otherYear = otherCalendar.get(Calendar.YEAR)
                val thisMonth = currentCalender.get(Calendar.MONTH) // Calendar.MONTH is 0-indexed
                val otherMonth = otherCalendar.get(Calendar.MONTH)

                if (thisYear < otherYear) {
                    true
                } else if (thisYear > otherYear) {
                    false
                } else {
                    thisMonth < otherMonth
                }
            }
            TimeUnit.YEARS -> {
                this.toCalendar().get(Calendar.YEAR) < date.toCalendar().get(Calendar.YEAR)
            }
        }
    }

    private fun isAfter(date: AppDate, unit: TimeUnit = TimeUnit.MILLIS): Boolean {
        return when (unit) {
            TimeUnit.MILLIS -> this.timeInMillis > date.timeInMillis
            TimeUnit.SECONDS -> (this.timeInMillis / 1000) > (date.timeInMillis / 1000)
            TimeUnit.MONTHS -> {
                val currentCalender = this.toCalendar()
                val otherCalendar = date.toCalendar()

                val thisYear = currentCalender.get(Calendar.YEAR)
                val otherYear = otherCalendar.get(Calendar.YEAR)
                val thisMonth = currentCalender.get(Calendar.MONTH) // Calendar.MONTH is 0-indexed
                val otherMonth = otherCalendar.get(Calendar.MONTH)

                if (thisYear > otherYear) {
                    true
                } else if (thisYear < otherYear) {
                    false
                } else {
                    thisMonth > otherMonth
                }
            }
            TimeUnit.YEARS -> {
                this.toCalendar().get(Calendar.YEAR) > date.toCalendar().get(Calendar.YEAR)
            }
        }
    }

    fun isBeforeOrEquals(date: AppDate, timeUnit: TimeUnit = TimeUnit.MILLIS) =
        !isAfter(date, timeUnit)

    fun isAfterOrEquals(date: AppDate, timeUnit: TimeUnit = TimeUnit.MILLIS) =
        !isBefore(date, timeUnit)

    companion object {
        fun now() = AppDate(Calendar.getInstance().timeInMillis)
    }

    enum class TimeUnit {
        MILLIS,
        SECONDS,
        MONTHS,
        YEARS
    }

    sealed interface Formatter {
        fun get(): String

        data class NumericMonth(val separator: String) : Formatter {
            override fun get() = "dd" + separator + "MM" + separator + "yyyy"
        }

        data class TextMonth(val separator: String) : Formatter {
            override fun get() = "dd" + separator + "MMM" + separator + "yyyy"
        }

        data object StandardTime : Formatter {
            override fun get() = "hh:mm a"
        }
    }
}