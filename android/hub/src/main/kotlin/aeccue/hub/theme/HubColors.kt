/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.theme

import aeccue.foundation.android.theme.ColorTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private val GREY_800 = Color(0xFF262626)
private val GREY_900 = Color(0xFF202020)

val HubColorTheme = ColorTheme(
    darkColors(
        primary = Color.White,
        primaryVariant = Color.White,
        secondary = Color.White,
        secondaryVariant = Color.White,
        surface = GREY_900,
        background = GREY_800
    ),
    lightColors(
        primary = Color.Black,
        primaryVariant = Color.Black,
        secondary = Color.Black,
        secondaryVariant = Color.Black
    )
)
