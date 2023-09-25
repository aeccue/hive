/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.dagger

import aeccue.foundation.android.peripheral.bluetooth.BluetoothIdentifier
import aeccue.foundation.dagger.scope.ScreenContainerScope
import aeccue.hub.led.model.LedGroup
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
interface LedBluetoothDaggerModule {

    companion object {

        @ScreenContainerScope
        @Provides
        @IntoSet
        fun deskAddress(): BluetoothIdentifier = LedGroup.Desk.bluetoothId
    }
}
