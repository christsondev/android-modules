package com.christsondev.printer.bluetooth

import android.bluetooth.BluetoothDevice
import kotlin.time.Duration

sealed interface ScanEvents {
    data object Idle: ScanEvents
    data class Scanning(val duration: Duration): ScanEvents
    data class Found(val device: BluetoothDevice): ScanEvents
    data object NotFound: ScanEvents
}