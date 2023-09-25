/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.bluetooth.dagger

import aeccue.foundation.android.module.ScreenContainerModule
import aeccue.foundation.android.peripheral.bluetooth.BluetoothIdentifier
import aeccue.foundation.dagger.scope.ScreenContainerScope
import aeccue.hub.bluetooth.BluetoothModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds

@Module
interface BluetoothDaggerModule {

    @Multibinds
    fun bluetoothIdentifiers(): Set<BluetoothIdentifier>

    @ScreenContainerScope
    @Binds
    @IntoSet
    fun bluetoothModule(module: BluetoothModule): ScreenContainerModule
}
