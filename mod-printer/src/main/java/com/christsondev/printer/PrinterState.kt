package com.christsondev.printer

import android.bluetooth.BluetoothDevice
import kotlin.time.Duration

sealed interface PrinterState {
    data object Off: PrinterState // Bluetooth OFF
    data object Idle: PrinterState // Bluetooth ON but not doing anything
    data class Scanning(val duration: Duration): PrinterState
    data object NotFound: PrinterState
    data class Found(val device: BluetoothDevice): PrinterState
    data object Pairing: PrinterState
    data class Connecting(val duration: Duration): PrinterState
    data object Connected: PrinterState
}