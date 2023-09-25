/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.module

import aeccue.foundation.android.module.ScreenContainerModule
import aeccue.foundation.android.navigation.BottomNavigationScreen
import aeccue.foundation.android.screen.NavHostScreen
import aeccue.foundation.android.screen.ScreenContainer
import aeccue.foundation.dagger.scope.ScreenContainerScope
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import javax.inject.Named

@Module(includes = [ViewModelsDaggerModule::class])
interface ScreenContainerDaggerModule {

    companion object {

        const val SCREENS = "ScreenContainer.Screens"

        @ScreenContainerScope
        @Provides
        fun bottomNavigationScreens(
            @Named(SCREENS) screens: Set<@JvmSuppressWildcards NavHostScreen>
        ): List<BottomNavigationScreen> =
            screens.filterIsInstance<BottomNavigationScreen>()
                .sortedBy { it.order }

        @ScreenContainerScope
        @Provides
        fun viewModelProvider(
            owner: ScreenContainer,
            factory: ViewModelProvider.Factory
        ) = ViewModelProvider(owner, factory)
    }

    @Multibinds
    fun modules(): Set<ScreenContainerModule>

    @Named(SCREENS)
    @Multibinds
    fun screens(): Set<NavHostScreen>
}
