package aeccue.foundation.android.compose.shape

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class Underline(private val side: Side) : Shape {

    enum class Side {
        Bottom, Left, Right, Top
    }

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val rect = when (side) {
                Side.Bottom -> {
                    Rect(0f, size.height - 1, size.width, size.height)
                }
                Side.Left -> {
                    Rect(0f, 0f, 1f, size.height)
                }
                Side.Right -> {
                    Rect(size.width - 1, 0f, size.width, size.height)
                }
                Side.Top -> {
                    Rect(0f, 0f, size.width, 1f)
                }
            }

            addRect(rect)
        }

        return Outline.Generic(path)
    }
}
