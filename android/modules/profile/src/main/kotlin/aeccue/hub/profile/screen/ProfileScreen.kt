/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.profile.screen

import aeccue.foundation.android.compose.ext.Icon
import aeccue.foundation.android.navigation.BottomNavigationScreen
import aeccue.foundation.android.navigation.NavRoute
import aeccue.foundation.android.screen.NavGraphScreen
import aeccue.foundation.android.screen.NavHostScreen
import aeccue.foundation.dagger.injector.InitializedDaggerInjected
import aeccue.hub.profile.dagger.ProfileScreensDaggerModule
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import javax.inject.Inject
import javax.inject.Named

class ProfileScreen : NavGraphScreen(), BottomNavigationScreen, InitializedDaggerInjected {

    object Route : NavRoute("profile")

    override val id = 2L
    override val name = "Profile"
    override val navRoute = Route
    override val route = Route.route
    override val startDestination = "hello"

    override val order = id.toInt()

    @Named(ProfileScreensDaggerModule.SCREENS)
    @Inject
    override lateinit var screens: Set<@JvmSuppressWildcards NavHostScreen>

    @Composable
    override fun SelectedIcon() {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            tint = MaterialTheme.colors.primary
        )
    }

    @Composable
    override fun UnselectedIcon() {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            tint = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.medium)
        )
    }
}
