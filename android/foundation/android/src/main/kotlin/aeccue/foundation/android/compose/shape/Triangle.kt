package aeccue.foundation.android.compose.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class Triangle(private val direction: Direction = Direction.UP) : Shape {

    enum class Direction {
        UP, DOWN, RIGHT, LEFT
    }

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            when (direction) {
                Direction.UP -> {
                    moveTo(size.width / 2f, 0f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                }
                Direction.DOWN -> {
                    lineTo(size.width, 0f)
                    lineTo(size.width / 2f, size.height)
                }
                Direction.RIGHT -> {
                    lineTo(size.width, size.height / 2f)
                    lineTo(0f, size.height)
                }
                Direction.LEFT -> {
                    moveTo(0f, size.height / 2f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height)
                }
            }

            close()
        }

        return Outline.Generic(path)
    }
}
