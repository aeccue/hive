/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.bluetooth

import aeccue.foundation.android.module.ScreenContainerModule
import aeccue.foundation.android.peripheral.bluetooth.BluetoothIdentifier
import aeccue.foundation.android.peripheral.bluetooth.BluetoothUUID
import aeccue.foundation.android.screen.ScreenContainer
import aeccue.foundation.dagger.scope.ScreenContainerScope
import aeccue.hub.bluetooth.broadcast.BluetoothBroadcastReceiver
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ScreenContainerScope
class BluetoothModule @Inject constructor(
    addresses: Set<@JvmSuppressWildcards BluetoothIdentifier>
) : ScreenContainerModule {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val toggle = MutableSharedFlow<Boolean>()
    private val state = MutableStateFlow(BluetoothAdapter.STATE_OFF)

    private val statuses: Map<BluetoothIdentifier, MutableStateFlow<Boolean>> =
        addresses.associateWith { MutableStateFlow(false) }

    private val sockets: Map<BluetoothIdentifier, MutableStateFlow<BluetoothSocket?>> =
        addresses.associateWith { MutableStateFlow(null) }

    private val receiver = BluetoothBroadcastReceiver(state, statuses)

    override fun init(context: ScreenContainer) {
        receiver.register(context)
        initializeBluetooth(context)
        initializeSockets()
    }

    override fun destroy(context: ScreenContainer) {
        receiver.unregister(context)
        clearSockets()
        scope.coroutineContext.cancelChildren()
    }

    fun status(id: BluetoothIdentifier): StateFlow<Boolean> {
        return statuses[id]?.asStateFlow() ?: MutableStateFlow(false).asStateFlow()
    }

    fun connect(id: BluetoothIdentifier) {
        scope.launch {
            when (state.value) {
                BluetoothAdapter.STATE_OFF -> {
                    toggle.emit(true)
                }
                BluetoothAdapter.STATE_ON -> {
                    runCatching {
                        sockets[id]?.value?.connect()
                    }
                }
            }
        }
    }

    fun socket(id: BluetoothIdentifier): StateFlow<BluetoothSocket?> =
        sockets[id]?.asStateFlow() ?: MutableStateFlow(null).asStateFlow()

    private fun initializeBluetooth(context: ScreenContainer) {
        val adapter = BluetoothAdapter.getDefaultAdapter() ?: return

        val launcher = context.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {}

        scope.launch {
            toggle.collectLatest { on ->
                if (on && !adapter.isEnabled) {
                    launcher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
                }
            }
        }

        scope.launch {
            if (adapter.isEnabled) state.value = BluetoothAdapter.STATE_ON
            else launcher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        }
    }

    private fun initializeSockets() {
        val adapter = BluetoothAdapter.getDefaultAdapter() ?: return

        scope.launch {
            state.collect { state ->
                when (state) {
                    BluetoothAdapter.STATE_TURNING_OFF, BluetoothAdapter.STATE_OFF -> {
                        clearSockets()
                    }
                    BluetoothAdapter.STATE_ON -> {
                        connectSockets(adapter)
                    }
                }
            }
        }
    }

    private fun clearSockets() {
        sockets.forEach { (_, socket) ->
            socket.value?.close()
            socket.value = null
        }
        statuses.forEach { (_, status) ->
            status.value = false
        }
    }

    private fun connectSockets(adapter: BluetoothAdapter) {
        sockets.forEach { (id, socket) ->
            val connection = socket.value.let {
                it ?: adapter.getRemoteDevice(id.bluetoothAddress)
                    .createRfcommSocketToServiceRecord(BluetoothUUID.SerialPort.uuid)
                    .also { newSocket ->
                        socket.value = newSocket
                    }
            }

            if (connection.isConnected) {
                statuses[id]?.value = true
            } else {
                runCatching {
                    connection.connect()
                }
            }
        }
    }
}
