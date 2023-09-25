package aeccue.foundation.android.compose.ext

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils

fun Color.Companion.fromHSL(hsl: FloatArray): Color = Color(hsl.toColor())

fun FloatArray.toColor(): Int = ColorUtils.HSLToColor(this)

fun Color.Companion.hslFromRgb(red: Int, green: Int, blue: Int) = FloatArray(3).apply {
    ColorUtils.RGBToHSL(red, green, blue, this)
}

val FloatArray.hue get() = this[0]
val FloatArray.saturation get() = this[1]
val FloatArray.lightness get() = this[2]

var MutableList<Float>.hue
    get() = this[0]
    set(value) {
        this[0] = value
    }
var MutableList<Float>.saturation
    get() = this[1]
    set(value) {
        this[1] = value
    }
var MutableList<Float>.lightness
    get() = this[2]
    set(value) {
        this[2] = value
    }

fun List<Byte>.colorAt(i: Int): Color = Color(
    red = get(i).toInt() and 0xFF,
    green = get(i + 1).toInt() and 0xFF,
    blue = get(i + 2).toInt() and 0xFF
)

fun MutableList<Byte>.updateColor(index: Int, color: Color) {
    set(index, (color.red * 255.0f + 0.5f).toInt().toByte())
    set(index + 1, (color.green * 255.0f + 0.5f).toInt().toByte())
    set(index + 2, (color.blue * 255.0f + 0.5f).toInt().toByte())
}

fun Int.toHSL() = FloatArray(3).also { ColorUtils.colorToHSL(this, it) }

fun Int.toHexColor(includeAlpha: Boolean = false): String = if (includeAlpha) {
    "%06X".format(this)
} else {
    "%06X".format(this and 0xFFFFFF)
}
