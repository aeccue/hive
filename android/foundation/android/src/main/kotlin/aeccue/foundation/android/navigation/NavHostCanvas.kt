/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

open class NavHostCanvas(
    val controller: NavController,
    private val graphBuilder: NavGraphBuilder
) {

    open fun draw(builder: NavGraphBuilder.() -> Unit) {
        graphBuilder.apply(builder)
    }
}
