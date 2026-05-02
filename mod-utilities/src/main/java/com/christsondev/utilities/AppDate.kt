package com.christsondev.utilities

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AppDate(val timeInMillis: Long) {

    /**
     * Formats the date using a raw [pattern].
     * Returns an empty string if [timeInMillis] is 0 or less.
     */
    fun format(pattern: String, locale: Locale = Locale.getDefault()): String =
        if (timeInMillis > 0) {
            SimpleDateFormat(pattern, locale).format(Date(timeInMillis))
        } else {
            ""
        }

    fun add(millis: Long) = AppDate(timeInMillis + millis)

    fun minus(millis: Long) = AppDate(timeInMillis - millis)

    fun toCalendar(): Calendar =
        Calendar.getInstance().apply { timeInMillis = this@AppDate.timeInMillis }

    /**
     * Internal helper to truncate time units for accurate comparison.
     */
    private fun truncateTo(unit: TimeUnit): Long {
        val cal = toCalendar()
        when (unit) {
            TimeUnit.MILLIS -> return timeInMillis
            TimeUnit.SECONDS -> cal.set(Calendar.MILLISECOND, 0)
            TimeUnit.DAYS -> {
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)
            }

            TimeUnit.MONTHS -> {
                cal.set(Calendar.DAY_OF_MONTH, 1)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)
            }

            TimeUnit.YEARS -> {
                cal.set(Calendar.MONTH, 0)
                cal.set(Calendar.DAY_OF_MONTH, 1)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)
            }
        }
        return cal.timeInMillis
    }

    fun isEqual(date: AppDate, unit: TimeUnit): Boolean =
        truncateTo(unit) == date.truncateTo(unit)

    fun isSameDay(other: AppDate) = isEqual(other, TimeUnit.DAYS)
    fun isSameMonth(other: AppDate) = isEqual(other, TimeUnit.MONTHS)
    fun isSameYear(other: AppDate) = isEqual(other, TimeUnit.YEARS)

    fun isBefore(date: AppDate, unit: TimeUnit = TimeUnit.MILLIS): Boolean =
        truncateTo(unit) < date.truncateTo(unit)

    fun isAfter(date: AppDate, unit: TimeUnit = TimeUnit.MILLIS): Boolean =
        truncateTo(unit) > date.truncateTo(unit)

    fun isBeforeOrEquals(date: AppDate, timeUnit: TimeUnit = TimeUnit.MILLIS) =
        !isAfter(date, timeUnit)

    fun isAfterOrEquals(date: AppDate, timeUnit: TimeUnit = TimeUnit.MILLIS) =
        !isBefore(date, timeUnit)

    fun isBetween(startDate: AppDate, endDate: AppDate, unit: TimeUnit) =
        isAfterOrEquals(startDate, unit) && isBeforeOrEquals(endDate, unit)

    companion object {
        fun now() = AppDate(System.currentTimeMillis())
    }

    enum class TimeUnit {
        MILLIS, SECONDS, DAYS, MONTHS, YEARS
    }
}
