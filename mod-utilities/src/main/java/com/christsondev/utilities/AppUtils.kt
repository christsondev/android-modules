package com.christsondev.utilities

import java.time.Instant
import java.time.ZoneId

object AppUtils {

    /**
     * @return positive = ahead UTC, negative = behind UTC
     */
    fun getLocalTimezoneOffsetMillis(): Long {
        val now = Instant.now()
        val localZoneId = ZoneId.systemDefault()
        val zonedDateTime = now.atZone(localZoneId)
        return zonedDateTime.offset.totalSeconds * 1000L
    }
}