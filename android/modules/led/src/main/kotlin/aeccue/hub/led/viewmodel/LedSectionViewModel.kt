/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.viewmodel

import aeccue.hub.led.api.LedSectionApi
import aeccue.hub.led.controller.LedController
import aeccue.hub.led.model.LedGroup
import aeccue.hub.led.model.LedSection
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LedSectionViewModel(
    private val api: LedSectionApi,
    private val ledController: LedController
) : ViewModel() {

    fun getSection(group: LedGroup, setupId: Int, sectionId: Int): Flow<LedSection> =
        api.get(group, setupId, sectionId)

    fun toggleSection(section: LedSection, on: Boolean) {
        viewModelScope.launch {
            api.update(section.copy(on = on))
            ledController.toggle(section, on)
        }
    }

    fun updateSection(section: LedSection) {
        viewModelScope.launch {
            api.update(section)
            if (section.on) ledController.set(section)
        }
    }
}
