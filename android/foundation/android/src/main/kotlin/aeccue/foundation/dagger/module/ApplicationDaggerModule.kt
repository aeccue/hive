/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.module

import aeccue.foundation.android.module.ProcessModule
import dagger.Module
import dagger.multibindings.Multibinds

@Module
interface ApplicationDaggerModule {

    @Multibinds
    fun processModules(): Set<ProcessModule>
}
