package aeccue.hub.led.viewmodel

import aeccue.hub.bluetooth.BluetoothModule
import aeccue.hub.led.api.LedSetupApi
import aeccue.hub.led.controller.LedController
import aeccue.hub.led.model.LedGroup
import aeccue.hub.led.model.LedSetup
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LedSetupsViewModel(
    private val api: LedSetupApi,
    private val bluetoothModule: BluetoothModule,
    private val ledController: LedController
) : ViewModel() {

    val setups: Flow<List<LedSetup>> = api.getAll()

    fun bluetoothStatus(group: LedGroup): StateFlow<Boolean> =
        bluetoothModule.status(group.bluetoothId)

    fun connectBluetooth(group: LedGroup) {
        bluetoothModule.connect(group.bluetoothId)
    }

    fun toggleSetup(setup: LedSetup, on: Boolean) {
        viewModelScope.launch {
            api.update(setup.copy(on = on))
            ledController.toggle(setup, on)
        }
    }

    fun createSetup(setup: LedSetup): Boolean {
        if (!setup.validate()) return false

        viewModelScope.launch {
            api.insert(setup)
        }

        return true
    }
}
