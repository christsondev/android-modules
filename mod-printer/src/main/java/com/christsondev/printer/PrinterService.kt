package com.christsondev.printer

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import com.christsondev.printer.bluetooth.BluetoothConnection
import com.christsondev.printer.bluetooth.BluetoothScanner
import com.christsondev.printer.bluetooth.BluetoothStateManager
import com.christsondev.printer.bluetooth.ConnectionEvents
import com.christsondev.printer.bluetooth.ScanEvents
import com.christsondev.utilities.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("MissingPermission")
@Singleton
class PrinterService @Inject constructor(
    private val bluetoothStateManager: BluetoothStateManager,
    private val bluetoothScanner: BluetoothScanner,
    private val bluetoothConnection: BluetoothConnection,
    dispatcherProvider: DispatcherProvider,
) {

    private val _state = MutableStateFlow<PrinterState>(PrinterState.Idle)
    val state = _state.asStateFlow()

    private val coroutineScope = CoroutineScope(dispatcherProvider.io())

    init {
        observeBluetoothState()
        observeScannerEvents()
        observeConnectionEvents()
    }

    fun start(addresses: List<String>) {
        bluetoothConnection.terminate()

        bluetoothScanner.scan(addresses)
    }

    fun terminate() {
        if (_state.value is PrinterState.Scanning) {
            bluetoothScanner.stopScan()
        }
        if (_state.value is PrinterState.Connecting || _state.value is PrinterState.Connected) {
            bluetoothConnection.terminate()
        }
    }

    /**
     * Send data to printer but does not print yet
     */
    suspend fun write(bytes: ByteArray) = bluetoothConnection.send(bytes)

    /**
     * Send data to printer and print
     */
    suspend fun print(message: String) = bluetoothConnection.send(message)

    suspend fun print(bitmap: Bitmap) = bluetoothConnection.send(bitmap)

    private fun observeBluetoothState() {
        coroutineScope.launch {
            bluetoothStateManager.isEnabled.collectLatest { isBluetoothEnabled ->
                if (!isBluetoothEnabled) {
                    _state.emit(PrinterState.Off)
                } else {
                    _state.emit(PrinterState.Idle)
                }
            }
        }

        coroutineScope.launch {
            bluetoothStateManager.events.consumeAsFlow().collectLatest { event ->
                when (event) {
                    BluetoothStateManager.Events.Disconnected -> {
                        _state.emit(PrinterState.Idle)
                    }
                    is BluetoothStateManager.Events.DeviceBonded -> {
                        bluetoothConnection.connect(event.device)
                    }
                }
            }
        }
    }

    private fun observeScannerEvents() {
        coroutineScope.launch {
            bluetoothScanner.events.consumeAsFlow().collectLatest { event ->
                when (event) {
                    is ScanEvents.Scanning -> {
                        _state.emit(PrinterState.Scanning(event.duration))
                    }
                    is ScanEvents.Found -> {
                        val device = event.device
                        _state.emit(PrinterState.Found(device))

                        bluetoothScanner.stopScan()
                        delay(1000)
                        if (device.bondState == BluetoothDevice.BOND_BONDED) {
                            bluetoothConnection.connect(device)
                        } else {
                            _state.emit(PrinterState.Pairing)
                            device.createBond()
                        }
                    }
                    ScanEvents.NotFound -> {
                        _state.emit(PrinterState.NotFound)
                    }
                    else -> {
                        _state.emit(PrinterState.Idle)
                    }
                }
            }
        }
    }

    private fun observeConnectionEvents() {
        coroutineScope.launch {
            bluetoothConnection.events.consumeAsFlow().collectLatest { event ->
                when (event) {
                    ConnectionEvents.Connected -> {
                        _state.emit(PrinterState.Connected)
                    }
                    is ConnectionEvents.Connecting -> {
                        _state.emit(PrinterState.Connecting(event.duration))
                    }
                    else -> {
                        _state.emit(PrinterState.Idle)
                    }
                }
            }
        }
    }
}