/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.bluetooth

import aeccue.foundation.android.module.ScreenContainerModule
import aeccue.foundation.android.peripheral.bluetooth.BluetoothIdentifier
import aeccue.foundation.android.screen.ScreenContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream

open class BluetoothCommand(
    val commandId: Int,
    val destination: BluetoothIdentifier,
    val send: (OutputStream.(waitForAck: () -> Unit) -> Unit)?
)

abstract class BluetoothController<C : BluetoothCommand>(
    protected val bluetooth: BluetoothModule
) : ScreenContainerModule {

    protected open val scope = CoroutineScope(Dispatchers.IO)
    protected open val commands = MutableSharedFlow<C>()

    override fun init(context: ScreenContainer) {
        scope.launch {
            commands.collect { command ->
                bluetooth.socket(command.destination).value?.apply {
                    runCatching {
                        outputStream.write(command.commandId)
                        sendCommand(outputStream, inputStream, command)
                    }
                }
            }
        }
    }

    override fun destroy(context: ScreenContainer) {
        scope.cancel()
    }

    protected open suspend fun sendCommand(command: C) {
        commands.emit(command)
    }

    protected abstract fun sendCommand(
        outputStream: OutputStream,
        inputStream: InputStream,
        command: C
    )

    protected fun InputStream.waitForAck() {
        while (true) {
            if (available() > 0 && read() == 255) {
                break
            }
        }
    }

    protected fun OutputStream.writeShort(value: Int) {
        write(value)
        write(value ushr 8)
    }
}