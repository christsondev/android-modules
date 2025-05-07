package com.christsondev.printer.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import javax.inject.Inject

class BluetoothStateReceiver @Inject constructor(
    private val bluetoothStateManager: BluetoothStateManager,
): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                bluetoothStateManager.updateBluetoothState(state == BluetoothAdapter.STATE_ON)
            }
            BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                val device = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE, BluetoothDevice::class.java)
                } else {
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                }

                val state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE)
                when (state) {
                    BluetoothDevice.BOND_BONDED -> {
                        device?.let { bluetoothStateManager.deviceBonded(device) }
                    }
                }
            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                bluetoothStateManager.disconnected()
            }
        }
    }
}