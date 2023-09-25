/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.dagger

import aeccue.foundation.dagger.key.ViewModelKey
import aeccue.hub.bluetooth.BluetoothModule
import aeccue.hub.led.api.LedSectionApi
import aeccue.hub.led.api.LedSetApi
import aeccue.hub.led.api.LedSetupApi
import aeccue.hub.led.controller.LedController
import aeccue.hub.led.viewmodel.LedSectionViewModel
import aeccue.hub.led.viewmodel.LedSetViewModel
import aeccue.hub.led.viewmodel.LedSetupsViewModel
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface LedViewModelProvidersDaggerModule {

    companion object {

        @Provides
        @IntoMap
        @ViewModelKey(LedSetupsViewModel::class)
        fun setupsViewModel(
            api: LedSetupApi,
            bluetoothModule: BluetoothModule,
            ledController: LedController
        ): ViewModel =
            LedSetupsViewModel(api, bluetoothModule, ledController)

        @Provides
        @IntoMap
        @ViewModelKey(LedSetViewModel::class)
        fun setViewModel(
            api: LedSetApi,
            ledController: LedController
        ): ViewModel = LedSetViewModel(api, ledController)

        @Provides
        @IntoMap
        @ViewModelKey(LedSectionViewModel::class)
        fun sectionViewModel(
            api: LedSectionApi,
            ledController: LedController
        ): ViewModel = LedSectionViewModel(api, ledController)
    }
}
