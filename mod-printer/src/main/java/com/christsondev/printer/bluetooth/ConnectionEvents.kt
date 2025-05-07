package com.christsondev.printer.bluetooth

import kotlin.time.Duration

sealed interface ConnectionEvents {
    data object Idle: ConnectionEvents
    data class Connecting(val duration: Duration): ConnectionEvents
    data object Connected: ConnectionEvents
    data object Failed: ConnectionEvents
}