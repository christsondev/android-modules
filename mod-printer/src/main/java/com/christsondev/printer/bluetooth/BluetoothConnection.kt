package com.christsondev.printer.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.graphics.Bitmap
import com.christsondev.printer.dependencyinjection.PrinterScope
import com.christsondev.utilities.CountdownTimer
import com.christsondev.utilities.DispatcherProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import java.nio.charset.Charset
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@SuppressLint("MissingPermission")
class BluetoothConnection @Inject constructor(
    @PrinterScope private val countdownTimer: CountdownTimer,
    @PrinterScope private val dispatcherProvider: DispatcherProvider,
) {
    val events = Channel<ConnectionEvents>()

    private var bluetoothSocket: BluetoothSocket? = null

    suspend fun connect(device: BluetoothDevice) = withContext(dispatcherProvider.io()) {
        countdownTimer.startTimer(
            delayMs = TIMEOUT,
            onTimeout = {
                terminate()
                events.trySend(ConnectionEvents.Failed)
            },
            onEachSecond = { currentMs ->
                val duration = currentMs.toDuration(DurationUnit.MILLISECONDS)
                events.trySend(ConnectionEvents.Connecting(duration))
            }
        )

        runCatching {
            device.uuids.firstOrNull()?.uuid.let { uuid ->
                val socket = device.createRfcommSocketToServiceRecord(uuid)
                socket.connect()
                bluetoothSocket = socket
                // once reach here it means connected
                countdownTimer.stopTimer()

                events.trySend(ConnectionEvents.Connected)
            }
        }.onFailure {
            terminate()
            events.trySend(ConnectionEvents.Failed)
        }
    }

    fun terminate() {
        countdownTimer.stopTimer()

        bluetoothSocket?.close()
        bluetoothSocket = null
    }

    suspend fun send(data: ByteArray) = withContext(dispatcherProvider.io()) {
        runCatching {
            bluetoothSocket?.let { socket ->
                val outputStream = socket.outputStream
                outputStream.write(data)
                outputStream.flush()
            }
        }.onFailure {
        }
    }

    suspend fun send(data: String) = withContext(dispatcherProvider.io()) {
        runCatching {
            bluetoothSocket?.let { socket ->
                val message = data.toByteArray(Charset.forName(CHARSET))

                val outputStream = socket.outputStream
                outputStream.write(message)
                val tail = byteArrayOf(10, 13, 0) // 10 = \n new line, 13 = \r move cursor to beginning, 0 = string terminator
                outputStream.write(tail)
                outputStream.flush()
            }
        }.onFailure {
        }
    }

    suspend fun send(bitmap: Bitmap) = withContext(dispatcherProvider.io()) {
        runCatching {
            bluetoothSocket?.let { socket ->
                val outputStream = socket.outputStream
                val byteArray = BluetoothImage(bitmap).toByteArray()
                outputStream.write(byteArray)
                outputStream.flush()
            }
        }.onFailure {
        }
    }

    private companion object {
        const val TIMEOUT = 10_000L
        const val CHARSET = "GBK"
    }
}