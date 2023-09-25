/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.navigation

import aeccue.foundation.android.navigation.ext.currentGraphRoute
import aeccue.foundation.android.navigation.ext.currentRoute
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavigation(navController: NavController, screens: List<BottomNavigationScreen>) {
    androidx.compose.material.BottomNavigation(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        for (screen in screens) {
            val selected =
                if (screen.route == navController.currentRoute()) true
                else screen.route == navController.currentGraphRoute()

            BottomNavigationItem(
                icon = { if (selected) screen.SelectedIcon() else screen.UnselectedIcon() },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

interface BottomNavigationScreen {

    val order: Int
    val route: String

    @Composable
    fun SelectedIcon()

    @Composable
    fun UnselectedIcon()
}
