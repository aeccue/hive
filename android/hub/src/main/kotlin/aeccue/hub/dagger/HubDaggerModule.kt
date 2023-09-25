/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.dagger

import aeccue.foundation.dagger.module.ApplicationDaggerModule
import aeccue.foundation.dagger.scope.ApplicationScope
import aeccue.hub.app.HubApplication
import android.content.Context
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        ApplicationDaggerModule::class,
        HubScreenContainerDaggerModule::class,
        HubDatabaseDaggerModule::class
    ]
)
interface HubDaggerModule {

    @ApplicationScope
    @Binds
    fun context(application: HubApplication): Context
}
