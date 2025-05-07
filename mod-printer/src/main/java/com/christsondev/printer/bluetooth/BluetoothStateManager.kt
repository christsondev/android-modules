package com.christsondev.printer.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.christsondev.printer.dependencyinjection.PrinterScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothStateManager @Inject constructor(
    @PrinterScope bluetoothAdapter: BluetoothAdapter,
) {
    private var _isEnabled = MutableStateFlow(bluetoothAdapter.isEnabled)
    val isEnabled = _isEnabled.asStateFlow()

    val events = Channel<Events>()

    fun updateBluetoothState(isEnabled: Boolean) {
        _isEnabled.tryEmit(isEnabled)
    }

    fun deviceBonded(device: BluetoothDevice) {
        events.trySend(Events.DeviceBonded(device))
    }

    fun disconnected() {
        events.trySend(Events.Disconnected)
    }

    sealed interface Events {
        data object Disconnected: Events
        data class DeviceBonded(val device: BluetoothDevice): Events
    }
}