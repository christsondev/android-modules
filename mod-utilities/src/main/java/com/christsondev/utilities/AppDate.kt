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

    fun isEqual(date: AppDate, unit: TimeUnit): Boolean {
        val thisCalendar = this.toCalendar()
        val otherCalendar = date.toCalendar()

        val thisYear = thisCalendar.get(Calendar.YEAR)
        val otherYear = otherCalendar.get(Calendar.YEAR)
        val thisMonth = thisCalendar.get(Calendar.MONTH) // Calendar.MONTH is 0-indexed
        val otherMonth = otherCalendar.get(Calendar.MONTH)

        return when (unit) {
            TimeUnit.MILLIS -> this.timeInMillis == date.timeInMillis
            TimeUnit.SECONDS -> (this.timeInMillis / 1000) == (date.timeInMillis / 1000)
            TimeUnit.DAYS -> {
                thisYear == otherYear &&
                    thisCalendar.get(Calendar.DAY_OF_YEAR) == otherCalendar.get(Calendar.DAY_OF_YEAR)
            }
            TimeUnit.MONTHS -> {
                thisYear == otherYear && thisMonth == otherMonth
            }
            TimeUnit.YEARS -> {
                thisYear == otherYear
            }
        }
    }

    fun isBefore(date: AppDate, unit: TimeUnit = TimeUnit.MILLIS): Boolean {
        val thisCalendar = this.toCalendar()
        val otherCalendar = date.toCalendar()

        val thisYear = thisCalendar.get(Calendar.YEAR)
        val otherYear = otherCalendar.get(Calendar.YEAR)
        val thisMonth = thisCalendar.get(Calendar.MONTH) // Calendar.MONTH is 0-indexed
        val otherMonth = otherCalendar.get(Calendar.MONTH)

        return when (unit) {
            TimeUnit.MILLIS -> this.timeInMillis < date.timeInMillis
            TimeUnit.SECONDS -> (this.timeInMillis / 1000) < (date.timeInMillis / 1000)
            TimeUnit.DAYS -> {
                if (thisYear < otherYear) {
                    true
                } else if (thisYear > otherYear) {
                    false
                } else {
                    thisCalendar.get(Calendar.DAY_OF_YEAR) < otherCalendar.get(Calendar.DAY_OF_YEAR)
                }
            }
            TimeUnit.MONTHS -> {
                if (thisYear < otherYear) {
                    true
                } else if (thisYear > otherYear) {
                    false
                } else {
                    thisMonth < otherMonth
                }
            }
            TimeUnit.YEARS -> {
                thisYear < otherYear
            }
        }
    }

    fun isAfter(date: AppDate, unit: TimeUnit = TimeUnit.MILLIS): Boolean {
        val thisCalendar = this.toCalendar()
        val otherCalendar = date.toCalendar()

        val thisYear = thisCalendar.get(Calendar.YEAR)
        val otherYear = otherCalendar.get(Calendar.YEAR)
        val thisMonth = thisCalendar.get(Calendar.MONTH) // Calendar.MONTH is 0-indexed
        val otherMonth = otherCalendar.get(Calendar.MONTH)

        return when (unit) {
            TimeUnit.MILLIS -> this.timeInMillis > date.timeInMillis
            TimeUnit.SECONDS -> (this.timeInMillis / 1000) > (date.timeInMillis / 1000)
            TimeUnit.DAYS -> {
                if (thisYear > otherYear) {
                    true
                } else if (thisYear < otherYear) {
                    false
                } else {
                    thisCalendar.get(Calendar.DAY_OF_YEAR) > otherCalendar.get(Calendar.DAY_OF_YEAR)
                }
            }
            TimeUnit.MONTHS -> {
                if (thisYear > otherYear) {
                    true
                } else if (thisYear < otherYear) {
                    false
                } else {
                    thisMonth > otherMonth
                }
            }
            TimeUnit.YEARS -> {
               thisYear > otherYear
            }
        }
    }

    fun isBeforeOrEquals(date: AppDate, timeUnit: TimeUnit = TimeUnit.MILLIS) =
        !isAfter(date, timeUnit)

    fun isAfterOrEquals(date: AppDate, timeUnit: TimeUnit = TimeUnit.MILLIS) =
        !isBefore(date, timeUnit)

    fun isBetween(startDate: AppDate, endDate: AppDate, unit: TimeUnit) =
        isAfterOrEquals(startDate, unit) && isBeforeOrEquals(endDate, unit)

    companion object {
        fun now() = AppDate(Calendar.getInstance().timeInMillis)
    }

    enum class TimeUnit {
        MILLIS,
        SECONDS,
        DAYS,
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