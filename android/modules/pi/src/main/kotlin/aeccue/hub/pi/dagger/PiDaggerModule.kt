/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.pi.dagger

import aeccue.foundation.android.screen.NavHostScreen
import aeccue.foundation.dagger.component.BaseDaggerSubcomponent
import aeccue.foundation.dagger.module.ScreenContainerDaggerModule
import aeccue.foundation.dagger.scope.ScreenContainerScope
import aeccue.foundation.dagger.scope.ScreenScope
import aeccue.hub.pi.screen.PiScreen
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoSet
import javax.inject.Named

@Module(
    subcomponents = [PiDaggerSubcomponent::class],
    includes = [PiDaggerSubcomponent.FactoryMapping::class]
)
interface PiDaggerModule {

    companion object {

        @ScreenContainerScope
        @Named(ScreenContainerDaggerModule.SCREENS)
        @Provides
        @IntoSet
        fun screen(): NavHostScreen = PiScreen()
    }
}

@ScreenScope
@Subcomponent
interface PiDaggerSubcomponent : BaseDaggerSubcomponent<PiScreen> {

    @Subcomponent.Factory
    abstract class Factory : BaseDaggerSubcomponent.Factory<PiScreen> {

        override val key = PiScreen::class.java
    }

    @Module
    interface FactoryMapping : BaseDaggerSubcomponent.SubcomponentFactoryMapping<PiScreen, Factory>
}
