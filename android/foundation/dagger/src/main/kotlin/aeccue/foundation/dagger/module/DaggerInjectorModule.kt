/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.module

import aeccue.foundation.dagger.component.BaseDaggerSubcomponent
import aeccue.foundation.dagger.injector.DaggerInjector
import dagger.Module
import dagger.multibindings.Multibinds

/**
 * A Dagger module that provides the dependencies for the [BaseDaggerSubcomponent.Factory] mappings
 * for the [DaggerInjector]. If a DaggerInjector is provided, this module must be added to the
 * Dagger component.
 */
@Module
interface DaggerInjectorModule {

    /**
     * Provides a set of all [BaseDaggerSubcomponent.Factory] provided.
     */
    @Multibinds
    fun factories(): Set<BaseDaggerSubcomponent.Factory<Any>>
}
