/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.dagger

import aeccue.foundation.android.module.ScreenContainerModule
import aeccue.foundation.android.screen.NavHostScreen
import aeccue.foundation.dagger.component.BaseDaggerSubcomponent
import aeccue.foundation.dagger.module.ScreenContainerDaggerModule
import aeccue.foundation.dagger.scope.ScreenContainerScope
import aeccue.foundation.dagger.scope.ScreenScope
import aeccue.hub.led.controller.LedController
import aeccue.hub.led.screen.LedScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoSet
import javax.inject.Named

@Module(
    subcomponents = [LedDaggerSubcomponent::class],
    includes = [LedDaggerSubcomponent.FactoryMapping::class]
)
interface LedDaggerModule {

    companion object {

        @ScreenContainerScope
        @Named(ScreenContainerDaggerModule.SCREENS)
        @Provides
        @IntoSet
        fun screen(): NavHostScreen = LedScreen()
    }

    @ScreenContainerScope
    @Binds
    @IntoSet
    fun ledController(controller: LedController): ScreenContainerModule
}

@ScreenScope
@Subcomponent(
    modules = [
        LedScreensDaggerModule::class,
        LedViewModelsDaggerModule::class
    ]
)
interface LedDaggerSubcomponent : BaseDaggerSubcomponent<LedScreen> {

    @Subcomponent.Factory
    abstract class Factory : BaseDaggerSubcomponent.Factory<LedScreen> {

        override val key = LedScreen::class.java
    }

    @Module
    interface FactoryMapping : BaseDaggerSubcomponent.SubcomponentFactoryMapping<LedScreen, Factory>
}
