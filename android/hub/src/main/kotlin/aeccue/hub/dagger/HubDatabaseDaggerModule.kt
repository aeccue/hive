/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.dagger

import aeccue.foundation.dagger.scope.ApplicationScope
import aeccue.hub.database.HubDatabase
import aeccue.hub.led.api.LedSectionApi
import aeccue.hub.led.api.LedSetApi
import aeccue.hub.led.api.LedSetupApi
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

@Module
interface HubDatabaseDaggerModule {

    companion object {

        @ApplicationScope
        @Provides
        fun database(context: Context): HubDatabase =
            Room.databaseBuilder(context, HubDatabase::class.java, HubDatabase.name)
                .build()

        @ApplicationScope
        @Provides
        fun ledSetupApi(database: HubDatabase): LedSetupApi = database.ledSetupApi()

        @ApplicationScope
        @Provides
        fun ledSetApi(setupApi: LedSetupApi, sectionApi: LedSectionApi): LedSetApi =
            LedSetApi(setupApi, sectionApi)

        @ApplicationScope
        @Provides
        fun ledSectionApi(database: HubDatabase): LedSectionApi = database.ledSectionApi()
    }
}
