package aeccue.foundation.android.compose.ui

import aeccue.foundation.android.compose.theme.RAINBOW_RING_HUES
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val RING_DEFAULT_THICKNESS = 4

@Composable
fun Ring(
    color: Color,
    modifier: Modifier = Modifier,
    thickness: Dp = RING_DEFAULT_THICKNESS.dp,
) {
    Canvas(modifier = modifier) {
        drawCircle(
            color = color,
            style = Stroke(width = thickness.toPx())
        )
    }
}

@Composable
fun RainbowRing(
    modifier: Modifier = Modifier,
    thickness: Dp = RING_DEFAULT_THICKNESS.dp,
    colors: Array<Pair<Float, Color>> = RAINBOW_RING_HUES
) {
    Canvas(modifier = modifier) {
        drawCircle(
            brush = Brush.sweepGradient(*colors),
            style = Stroke(width = thickness.toPx())
        )
    }
}
