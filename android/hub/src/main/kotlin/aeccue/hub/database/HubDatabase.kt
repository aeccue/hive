/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.database

import aeccue.hub.led.api.LedSectionApi
import aeccue.hub.led.api.LedSetupApi
import aeccue.hub.led.model.LedSection
import aeccue.hub.led.model.LedSectionConfiguration
import aeccue.hub.led.model.LedSetup
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [LedSetup::class, LedSection::class, LedSectionConfiguration::class],
    exportSchema = false
)
abstract class HubDatabase : RoomDatabase() {

    companion object {
        const val name = "HubDatabase"
    }

    abstract fun ledSetupApi(): LedSetupApi
    abstract fun ledSectionApi(): LedSectionApi
}
