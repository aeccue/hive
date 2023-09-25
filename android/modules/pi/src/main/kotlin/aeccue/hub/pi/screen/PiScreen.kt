/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.pi.screen

import aeccue.foundation.android.compose.ext.Icon
import aeccue.foundation.android.navigation.BottomNavigationScreen
import aeccue.foundation.android.navigation.NavRoute
import aeccue.foundation.android.screen.NavComposableScreen
import aeccue.foundation.dagger.injector.InitializedDaggerInjected
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

class PiScreen : NavComposableScreen(), BottomNavigationScreen, InitializedDaggerInjected {

    object Route : NavRoute("pi")

    override val id = 1L
    override val name = "Pi"
    override val navRoute = Route
    override val route = Route.route

    override val order = id.toInt()

    @Composable
    override fun Screen(navController: NavController, backStackEntry: NavBackStackEntry) {
    }

    @Composable
    override fun SelectedIcon() {
        Icon(
            imageVector = Icons.Filled.Computer,
            tint = MaterialTheme.colors.primary
        )
    }

    @Composable
    override fun UnselectedIcon() {
        Icon(
            imageVector = Icons.Filled.Computer,
            tint = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.medium)
        )
    }
}
