/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.bluetooth.broadcast

import aeccue.foundation.android.peripheral.bluetooth.BluetoothIdentifier
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.flow.MutableStateFlow

class BluetoothBroadcastReceiver(
    private val state: MutableStateFlow<Int>,
    private val statuses: Map<BluetoothIdentifier, MutableStateFlow<Boolean>>
) : BroadcastReceiver() {

    fun register(context: Context) {
        val intent = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        }
        context.registerReceiver(this, intent)
    }

    fun unregister(context: Context) {
        context.unregisterReceiver(this)
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                state.value = intent.getIntExtra(
                    BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.STATE_OFF
                )
            }
            BluetoothDevice.ACTION_ACL_CONNECTED, BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                val device: BluetoothDevice =
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) ?: return
                val id = BluetoothIdentifier(device.name, device.address)
                statuses[id]?.value = intent.action == BluetoothDevice.ACTION_ACL_CONNECTED
            }
        }
    }
}
