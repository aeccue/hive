/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.screen

import aeccue.foundation.android.navigation.NavHostCanvas
import aeccue.foundation.android.navigation.ext.navigation

abstract class NavGraphScreen : NavHostScreen {

    protected abstract val startDestination: String
    protected abstract val screens: Set<NavHostScreen>

    override fun display(canvas: NavHostCanvas) {
        canvas.draw {
            navigation(
                startDestination = startDestination,
                navRoute = navRoute
            ) {
                val nestedCanvas = NavHostCanvas(canvas.controller, this)
                for (screen in screens) {
                    screen.display(nestedCanvas)
                }
            }
        }
    }
}
