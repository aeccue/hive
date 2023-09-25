/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */


package aeccue.hub.led.screen

import aeccue.foundation.android.compose.ext.Icon
import aeccue.foundation.android.navigation.BottomNavigationScreen
import aeccue.foundation.android.navigation.NavRoute
import aeccue.foundation.android.screen.NavGraphScreen
import aeccue.foundation.android.screen.NavHostScreen
import aeccue.foundation.dagger.injector.InitializedDaggerInjected
import aeccue.hub.led.dagger.LedScreensDaggerModule
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.runtime.Composable
import javax.inject.Inject
import javax.inject.Named

class LedScreen : NavGraphScreen(), BottomNavigationScreen, InitializedDaggerInjected {

    companion object {

        const val NAME = "LED"
    }

    object Route : NavRoute("led")

    override val id = 0L
    override val name = NAME
    override val navRoute = Route
    override val route = Route.route
    override val startDestination = LedSetupsScreen.Route.route

    override val order = id.toInt()

    @Named(LedScreensDaggerModule.SCREENS)
    @Inject
    override lateinit var screens: Set<@JvmSuppressWildcards NavHostScreen>

    @Composable
    override fun SelectedIcon() {
        Icon(
            imageVector = Icons.Filled.Lightbulb,
            tint = MaterialTheme.colors.primary
        )
    }

    @Composable
    override fun UnselectedIcon() {
        Icon(
            imageVector = Icons.Filled.Lightbulb,
            tint = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.medium)
        )
    }
}
