package aeccue.hub.led.screen

import aeccue.foundation.android.compose.ColorPickerScreen
import aeccue.hub.led.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun LedColorEditScreen(initialColor: Color, onSet: (Color) -> Unit, onDismiss: () -> Unit) {
    ColorPickerScreen(
        initialColor = initialColor,
        title = stringResource(R.string.title_edit_color),
        onColorPick = onSet,
        onDismiss = onDismiss,
        modifier = Modifier.fillMaxSize()
    )
}
