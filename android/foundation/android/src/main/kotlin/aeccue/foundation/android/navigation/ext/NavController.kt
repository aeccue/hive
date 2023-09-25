/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.navigation.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavController.currentRoute(): String? {
    val backStackEntry by currentBackStackEntryAsState()
    return backStackEntry?.destination?.route
}

@Composable
fun NavController.currentGraphRoute(): String? {
    val backStackEntry by currentBackStackEntryAsState()
    return backStackEntry?.destination?.parent?.route
}
