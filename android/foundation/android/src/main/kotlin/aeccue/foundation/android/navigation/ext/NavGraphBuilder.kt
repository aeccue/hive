package aeccue.foundation.android.navigation.ext

import aeccue.foundation.android.navigation.NavRoute
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

inline fun NavGraphBuilder.navigation(
    startDestination: String,
    navRoute: NavRoute,
    builder: NavGraphBuilder.() -> Unit
) {
    navigation(
        startDestination = startDestination,
        route = navRoute.route,
        builder = builder
    )
}

fun NavGraphBuilder.composable(
    navRoute: NavRoute,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navRoute.route,
        arguments = navRoute.arguments,
        deepLinks = navRoute.deepLinks,
        content = content
    )
}
