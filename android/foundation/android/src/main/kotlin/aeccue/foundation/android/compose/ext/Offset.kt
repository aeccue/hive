package aeccue.foundation.android.compose.ext

import androidx.compose.ui.geometry.Offset
import kotlin.math.*

fun Offset.containWithinCircle(radius: Float): Offset {
    if (getDistance() <= radius) return this

    val slope = y / x
    var containedX = abs(radius / sqrt(slope.pow(2) + 1))
    var containedY = abs(slope * containedX)

    if (x < 0) containedX = -containedX
    if (y < 0) containedY = -containedY

    return Offset(containedX, containedY)
}

fun Offset.angleFromInvertedYAxis(): Float {
    val angleRad = atan2(0f, -1f) - atan2(x, y)
    return (angleRad * 180f / Math.PI).toFloat()
}

fun Offset.Companion.vectorOnInvertedYAxisFrom(angle: Float, length: Float): Offset {
    if (length == 0f) return Offset(0f, 0f)

    val angleRad = angle * Math.PI / 180

    val slope = tan(atan2(0f, -1f) - angleRad).toFloat()
    var y = abs(length / sqrt(slope.pow(2) + 1))
    var x = abs(slope * y)

    if (angleRad > Math.PI) x = -x
    if (angleRad < Math.PI / 2f || angleRad > Math.PI * 1.5f) y = -y

    return Offset(x, y)
}
