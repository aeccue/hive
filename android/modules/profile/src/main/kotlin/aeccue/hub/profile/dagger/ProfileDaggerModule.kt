/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.profile.dagger

import aeccue.foundation.android.screen.NavHostScreen
import aeccue.foundation.dagger.component.BaseDaggerSubcomponent
import aeccue.foundation.dagger.module.ScreenContainerDaggerModule
import aeccue.foundation.dagger.scope.ScreenContainerScope
import aeccue.foundation.dagger.scope.ScreenScope
import aeccue.hub.profile.screen.ProfileScreen
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoSet
import javax.inject.Named

@Module(
    subcomponents = [ProfileDaggerSubcomponent::class],
    includes = [ProfileDaggerSubcomponent.FactoryMapping::class]
)
interface ProfileDaggerModule {

    companion object {

        @ScreenContainerScope
        @Named(ScreenContainerDaggerModule.SCREENS)
        @Provides
        @IntoSet
        fun screen(): NavHostScreen = ProfileScreen()
    }
}

@ScreenScope
@Subcomponent(
    modules = [
        ProfileScreensDaggerModule::class,
        ProfileViewModelsDaggerModule::class
    ]
)
interface ProfileDaggerSubcomponent : BaseDaggerSubcomponent<ProfileScreen> {

    @Subcomponent.Factory
    abstract class Factory : BaseDaggerSubcomponent.Factory<ProfileScreen> {

        override val key = ProfileScreen::class.java
    }

    @Module
    interface FactoryMapping :
        BaseDaggerSubcomponent.SubcomponentFactoryMapping<ProfileScreen, Factory>
}
