/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.screen

import aeccue.foundation.android.navigation.NavHostCanvas
import aeccue.foundation.android.navigation.ext.composable
import aeccue.foundation.util.INVALID_VALUE
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

abstract class NavComposableScreen : NavHostScreen {

    override val id = Long.INVALID_VALUE

    override fun display(canvas: NavHostCanvas) {
        canvas.draw {
            composable(
                navRoute = navRoute
            ) {
                Screen(canvas.controller, it)
            }
        }
    }

    @Composable
    protected abstract fun Screen(navController: NavController, backStackEntry: NavBackStackEntry)
}
