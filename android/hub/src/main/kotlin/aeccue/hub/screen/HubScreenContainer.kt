/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.screen

import aeccue.foundation.android.compose.theme.ExtendedMaterialTheme
import aeccue.foundation.android.screen.ScreenContainer
import aeccue.hub.theme.HubColorTheme
import aeccue.hub.theme.HubDimensions
import aeccue.hub.theme.HubShapes
import aeccue.hub.theme.HubTypography
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

class HubScreenContainer : ScreenContainer() {

    @Composable
    override fun Theme(content: @Composable () -> Unit) {
        ExtendedMaterialTheme(
            colors = if (isSystemInDarkTheme()) HubColorTheme.dark else HubColorTheme.light,
            typography = HubTypography,
            shapes = HubShapes,
            dimensions = HubDimensions
        ) {
            ScreenContainer(rememberNavController())
        }
    }
}
