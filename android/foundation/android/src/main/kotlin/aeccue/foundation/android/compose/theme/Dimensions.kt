package aeccue.foundation.android.compose.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimensions(
    val borderWidth: Dp = 1.dp,

    val paddingSmall: Dp = 8.dp,
    val paddingIconSystem: Dp = 12.dp,
    val paddingDefault: Dp = 16.dp,
    val paddingLarge: Dp = 32.dp,

    val iconSizeDefault: Dp = 64.dp,
    val iconSizeSystem: Dp = 48.dp,
    val iconSizeSystemNoPadding: Dp = 24.dp,
    val iconLarge: Dp = 128.dp,

    val listItemHeight: Dp = 64.dp
)

internal val LocalDimensions = staticCompositionLocalOf { Dimensions() }
