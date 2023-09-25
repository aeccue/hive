/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.dagger

import aeccue.hub.bluetooth.dagger.BluetoothDaggerModule
import aeccue.hub.led.dagger.LedBluetoothDaggerModule
import dagger.Module

@Module(
    includes = [
        BluetoothDaggerModule::class,
        LedBluetoothDaggerModule::class
    ]
)
interface HubBluetoothDaggerModule
