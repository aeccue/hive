/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.dagger

import aeccue.foundation.android.screen.ScreenContainer
import aeccue.foundation.dagger.component.BaseDaggerSubcomponent
import aeccue.foundation.dagger.module.DaggerInjectorModule
import aeccue.foundation.dagger.module.ScreenContainerDaggerModule
import aeccue.foundation.dagger.scope.ScreenContainerScope
import aeccue.foundation.dagger.scope.SubcomponentScope
import aeccue.hub.screen.HubScreenContainer
import dagger.Module
import dagger.Subcomponent

@Module(
    subcomponents = [HubScreenContainerDaggerSubcomponent::class],
    includes = [HubScreenContainerDaggerSubcomponent.FactoryMapping::class]
)
interface HubScreenContainerDaggerModule

@SubcomponentScope
@ScreenContainerScope
@Subcomponent(
    modules = [
        DaggerInjectorModule::class,
        ScreenContainerDaggerModule::class,
        HubBluetoothDaggerModule::class,
        HubScreensDaggerModule::class,
        HubViewModelProviderDaggerModule::class
    ]
)
interface HubScreenContainerDaggerSubcomponent : BaseDaggerSubcomponent<ScreenContainer> {

    @Subcomponent.Factory
    abstract class Factory : BaseDaggerSubcomponent.Factory<ScreenContainer> {

        override val key = HubScreenContainer::class.java
    }

    @Module
    interface FactoryMapping :
        BaseDaggerSubcomponent.ComponentFactoryMapping<ScreenContainer, Factory>
}
