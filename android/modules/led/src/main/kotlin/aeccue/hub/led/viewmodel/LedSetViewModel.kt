/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.viewmodel

import aeccue.hub.led.api.LedSetApi
import aeccue.hub.led.controller.LedController
import aeccue.hub.led.model.LedGroup
import aeccue.hub.led.model.LedSection
import aeccue.hub.led.model.LedSet
import aeccue.hub.led.model.LedSetup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LedSetViewModel(
    private val api: LedSetApi,
    private val ledController: LedController
) : ViewModel() {

    fun getAllSections(group: LedGroup, id: Int): Flow<LedSet> = api.section.getAll(group, id)

    fun toggleSet(set: LedSet, on: Boolean) {
        viewModelScope.launch {
            api.setup.update(set.setup.copy(on = on))
            set.sections.forEach { section ->
                ledController.toggle(section, on)
            }
        }
    }

    fun toggleSection(section: LedSection, on: Boolean) {
        viewModelScope.launch {
            api.section.update(section.copy(on = on))
            ledController.toggle(section, on)
        }
    }

    fun createSection(section: LedSection): Boolean {
        if (!section.validate()) return false

        viewModelScope.launch {
            api.section.insert(section)
        }

        return true
    }
}
