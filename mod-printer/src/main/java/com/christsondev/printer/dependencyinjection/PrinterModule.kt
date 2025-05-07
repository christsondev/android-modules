package com.christsondev.printer.dependencyinjection

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.christsondev.utilities.CountdownTimer
import com.christsondev.utilities.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PrinterModule {

    @Provides
    @PrinterScope
    fun provideBluetoothManager(@ApplicationContext context: Context): BluetoothManager {
        return context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    }

    @Provides
    @PrinterScope
    fun provideBluetoothAdapter(@PrinterScope bluetoothManager: BluetoothManager): BluetoothAdapter {
        return bluetoothManager.adapter
    }

    @Provides
    @PrinterScope
    fun provideDispatcherProvider() = DispatcherProvider

    @Provides
    @PrinterScope
    fun provideCountdownTimer(@PrinterScope dispatcherProvider: DispatcherProvider) =
        CountdownTimer(dispatcherProvider)
}