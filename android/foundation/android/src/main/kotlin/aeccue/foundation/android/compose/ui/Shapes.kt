package aeccue.foundation.android.compose.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Circle(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawCircle(color)
    }
}

@Composable
fun Rectangle(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawRect(color)
    }
}
