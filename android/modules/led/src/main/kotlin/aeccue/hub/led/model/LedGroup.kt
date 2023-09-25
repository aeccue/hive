/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.model

import aeccue.foundation.android.peripheral.bluetooth.BluetoothIdentifier

enum class LedGroup(val bluetoothId: BluetoothIdentifier) {

    Desk(BluetoothIdentifier("Desk Controller", "00:20:12:08:C1:26")),
    Bed(BluetoothIdentifier("Bed Controller", "")),
    Closet(BluetoothIdentifier("Closet Controller", ""))
}
