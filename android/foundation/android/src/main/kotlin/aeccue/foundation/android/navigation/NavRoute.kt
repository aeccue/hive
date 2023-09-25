package aeccue.foundation.android.navigation

import androidx.navigation.NavDeepLink
import androidx.navigation.compose.NamedNavArgument

open class NavRoute(
    val rootRoute: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val deepLinks: List<NavDeepLink> = emptyList()
) {

    val route: String = "$rootRoute${
        if (arguments.isNotEmpty()) {
            "?${
                arguments.joinToString("&") {
                    "${it.name}={${it.name}}"
                }
            }"
        } else ""
    }"

    protected fun create(args: Array<String>): String =
        "$rootRoute${
            if (arguments.isNotEmpty()) {
                "?${
                    arguments.withIndex().joinToString("&") {
                        "${it.value.name}=${args[it.index]}"
                    }
                }"
            } else ""
        }"
}
