/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.controller

import aeccue.foundation.android.peripheral.bluetooth.BluetoothIdentifier
import aeccue.foundation.dagger.scope.ScreenContainerScope
import aeccue.hub.bluetooth.BluetoothCommand
import aeccue.hub.bluetooth.BluetoothController
import aeccue.hub.bluetooth.BluetoothModule
import aeccue.hub.led.model.BYTES_PER_LED
import aeccue.hub.led.model.LedGroup
import aeccue.hub.led.model.LedSection
import aeccue.hub.led.model.LedSetup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

private const val COMMAND_ID_LED = 0
private const val LED_ID_UNSPECIFIED = -1

enum class LedAction(val value: Int) {
    Off(0),
    On(1),
    Set(2),
    SetAll(3)
}

class LedCommand(
    val setupId: Int,
    val sectionId: Int,
    val action: LedAction,
    destination: BluetoothIdentifier,
    send: (OutputStream.(waitForAck: () -> Unit) -> Unit)?
) : BluetoothCommand(COMMAND_ID_LED, destination, send)

@ScreenContainerScope
class LedController @Inject constructor(
    bluetooth: BluetoothModule
) : BluetoothController<LedCommand>(bluetooth) {

    suspend fun toggle(setup: LedSetup, on: Boolean) {
        sendCommand(
            group = setup.group,
            setupId = setup.id,
            action = if (on) LedAction.On else LedAction.Off
        )
    }

    suspend fun toggle(section: LedSection, on: Boolean) {
        sendCommand(
            group = section.group,
            setupId = section.setupId,
            sectionId = section.id,
            action = if (on) LedAction.On else LedAction.Off
        )
    }

    suspend fun set(section: LedSection) {
        sendCommand(
            group = section.group,
            setupId = section.setupId,
            sectionId = section.id,
            action = LedAction.Set,
            send = { waitForAck ->
                writeShort(section.ledCount)
                writeShort(0)

                waitForAck()

                val buffer = ByteArray(3)
                for (i in 0 until section.ledCount) {
                    buffer[0] = section.leds[i * BYTES_PER_LED]
                    buffer[1] = section.leds[i * BYTES_PER_LED + 1]
                    buffer[2] = section.leds[i * BYTES_PER_LED + 2]
                    write(buffer)

                    waitForAck()
                }
            }
        )
    }

    suspend fun setAll(setup: LedSetup, color: Color) {
        sendCommand(
            group = setup.group,
            setupId = setup.id,
            action = LedAction.SetAll
        ) {
            with(color.toArgb()) {
                write(red)
                write(green)
                write(blue)
            }
        }
    }

    suspend fun setAll(section: LedSection, color: Color) {
        sendCommand(
            group = section.group,
            setupId = section.setupId,
            sectionId = section.id,
            action = LedAction.SetAll
        ) {
            with(color.toArgb()) {
                write(red)
                write(green)
                write(blue)
            }
        }
    }

    private suspend fun sendCommand(
        group: LedGroup,
        setupId: Int = LED_ID_UNSPECIFIED,
        sectionId: Int = LED_ID_UNSPECIFIED,
        action: LedAction,
        send: (OutputStream.(waitForAck: () -> Unit) -> Unit)? = null,
    ) {
        sendCommand(
            LedCommand(
                destination = group.bluetoothId,
                setupId = setupId,
                sectionId = sectionId,
                action = action,
                send = send
            )
        )
    }

    override fun sendCommand(
        outputStream: OutputStream,
        inputStream: InputStream,
        command: LedCommand
    ) {
        outputStream.apply {
            write(command.setupId)
            write(command.sectionId)
            write(command.action.value)
        }

        inputStream.waitForAck()

        command.send?.invoke(outputStream) {
            inputStream.waitForAck()
        }
    }
}
