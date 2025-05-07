package com.christsondev.printer.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import com.christsondev.printer.dependencyinjection.PrinterScope
import com.christsondev.utilities.CountdownTimer
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@SuppressLint("MissingPermission")
class BluetoothScanner @Inject constructor(
    @PrinterScope private val countdownTimer: CountdownTimer,
    @PrinterScope private val bluetoothAdapter: BluetoothAdapter,
): ScanCallback() {

    val events = Channel<ScanEvents>()

    private val bluetoothLeScanner: BluetoothLeScanner
        get() = bluetoothAdapter.bluetoothLeScanner
    private var bluetoothScanCallback: ScanCallback = this

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
        .build()

    fun scan(address: List<String>) {
        bluetoothLeScanner.startScan(
            address.toScanFilters(),
            scanSettings,
            bluetoothScanCallback,
        )

        countdownTimer.startTimer(
            delayMs = TIMEOUT,
            onTimeout = {
                stopScan()
                events.trySend(ScanEvents.NotFound)
            },
            onEachSecond = { currentMs ->
                val duration = currentMs.toDuration(DurationUnit.MILLISECONDS)
                events.trySend(ScanEvents.Scanning(duration))
            },
        )
    }

    fun stopScan() {
        countdownTimer.stopTimer()
        bluetoothLeScanner.stopScan(bluetoothScanCallback)
    }

    override fun onScanResult(callbackType: Int, result: ScanResult) {
        events.trySend(ScanEvents.Found(result.device))
    }

    private fun List<String>.toScanFilters() = map { address ->
        ScanFilter.Builder().setDeviceAddress(address).build()
    }

    private companion object {
        const val TIMEOUT = 10_000L
    }
}