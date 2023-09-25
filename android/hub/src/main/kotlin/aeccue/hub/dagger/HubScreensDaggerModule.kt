/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.dagger

import aeccue.hub.led.dagger.LedDaggerModule
import aeccue.hub.pi.dagger.PiDaggerModule
import aeccue.hub.profile.dagger.ProfileDaggerModule
import dagger.Module

@Module(
    includes = [
        LedDaggerModule::class,
        PiDaggerModule::class,
        ProfileDaggerModule::class
    ]
)
interface HubScreensDaggerModule
