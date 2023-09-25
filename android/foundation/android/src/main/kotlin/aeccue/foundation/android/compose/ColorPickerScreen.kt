package aeccue.foundation.android.compose

import aeccue.foundation.android.R
import aeccue.foundation.android.compose.ext.*
import aeccue.foundation.android.compose.theme.RAINBOW_HUES
import aeccue.foundation.android.compose.theme.dimensions
import aeccue.foundation.android.compose.ui.Ring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.core.graphics.*
import java.util.*
import kotlin.math.min

private enum class Configurer {
    HSL, RGB, HEX
}

private object ColorPickerScreenDefaults {
    const val WIDTH = 256
}

@Composable
fun ColorPickerScreen(
    initialColor: Color,
    title: String,
    onColorPick: (Color) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = ColorPickerScreenDefaults.WIDTH.dp
) {
    val hsl = remember(initialColor) {
        mutableStateListOf(
            *FloatArray(3).apply {
                ColorUtils.colorToHSL(initialColor.toArgb(), this)
            }.toTypedArray()
        )
    }

    InputDialog(
        title = title,
        inputTitle = stringResource(R.string.button_set),
        onInput = { onColorPick(Color(ColorUtils.HSLToColor(hsl.toFloatArray()))) },
        onDismiss = onDismiss
    ) {
        ColorPicker(
            hsl = hsl,
            width = width,
            modifier = modifier
        )
    }
}

@Composable
private fun ColorPicker(
    hsl: MutableList<Float>,
    width: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(MaterialTheme.dimensions.paddingDefault),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColorWheel(hsl = hsl, width = width)
        ColorSpaceConfigurer(hsl = hsl)
    }
}

@Composable
private fun ColorWheel(
    hsl: MutableList<Float>,
    width: Dp
) {
    var centerPoint by remember { mutableStateOf<Offset?>(null) }
    val widthPx = LocalDensity.current.run { width.toPx() }
    val offset = remember(hsl.hue, hsl.saturation, widthPx) {
        Offset.vectorOnInvertedYAxisFrom(hsl.hue, hsl.saturation * widthPx / 2)
    }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(CircleShape)
            .border(
                width = MaterialTheme.dimensions.paddingDefault,
                color = Color.fromHSL(hsl.toFloatArray()),
                shape = CircleShape
            )
            .padding(MaterialTheme.dimensions.paddingDefault)
    ) {
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .onGloballyPositioned {
                    val x = it.size.width / 2f
                    val y = it.size.height / 2f
                    centerPoint = Offset(x, y)
                }
                .padding(MaterialTheme.dimensions.iconSizeSystemNoPadding)
                .size(width)
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        centerPoint?.let {
                            val position =
                                (change.position - it).containWithinCircle(width.toPx() / 2f)
                            hsl.hue = position.angleFromInvertedYAxis()
                            hsl.saturation = position.getDistance() * 2 / width.toPx()
                        }
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures(onPress = { newPosition ->
                        centerPoint?.let {
                            val position =
                                (newPosition - it).containWithinCircle(width.toPx() / 2f)
                            hsl.hue = position.angleFromInvertedYAxis()
                            hsl.saturation = position.getDistance() * 2 / width.toPx()
                        }
                    })
                }
        ) {
            drawCircle(brush = Brush.sweepGradient(*RAINBOW_HUES))
            drawCircle(brush = Brush.radialGradient(listOf(Color.White, Color(0x00FFFFFF))))
            hsl.lightness.let { lightness ->
                if (lightness < 0.5) {
                    drawCircle(Color.Black.copy(alpha = (0.5f - lightness) / 0.5f))
                } else if (lightness > 0.5) {
                    drawCircle(Color.White.copy(alpha = (lightness - 0.5f) / 0.5f))
                }
            }
        }

        Ring(
            color = Color.White,
            modifier = Modifier
                .offset { offset.round() }
                .size(MaterialTheme.dimensions.iconSizeSystemNoPadding)
                .align(Alignment.Center),
            thickness = 2.dp
        )
    }
}

@Composable
private fun ColorSpaceConfigurer(hsl: MutableList<Float>) {
    var configurer by remember { mutableStateOf(Configurer.HSL) }
    Row(
        modifier = Modifier
            .padding(top = MaterialTheme.dimensions.paddingDefault)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val colorSelected = MaterialTheme.colors.onBackground
        val colorDeselected =
            MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
        Text(
            text = Configurer.HSL.name,
            modifier = Modifier
                .clickable { configurer = Configurer.HSL }
                .padding(MaterialTheme.dimensions.paddingSmall),
            color = if (configurer == Configurer.HSL) colorSelected else colorDeselected,
            fontWeight = if (configurer == Configurer.HSL) FontWeight.Bold else null
        )
        Text(
            text = Configurer.RGB.name,
            modifier = Modifier
                .clickable { configurer = Configurer.RGB }
                .padding(MaterialTheme.dimensions.paddingSmall),
            color = if (configurer == Configurer.RGB) colorSelected else colorDeselected,
            fontWeight = if (configurer == Configurer.RGB) FontWeight.Bold else null
        )
        Text(
            text = Configurer.HEX.name,
            modifier = Modifier
                .clickable { configurer = Configurer.HEX }
                .padding(MaterialTheme.dimensions.paddingSmall),
            color = if (configurer == Configurer.HEX) colorSelected else colorDeselected,
            fontWeight = if (configurer == Configurer.HEX) FontWeight.Bold else null
        )
    }

    val color = remember(hsl.hue, hsl.saturation, hsl.lightness) { hsl.toFloatArray().toColor() }
    when (configurer) {
        Configurer.HSL -> HSLConfigurer(hsl = hsl)
        Configurer.RGB -> {
            val red = remember(color) { color.red }
            val green = remember(color) { color.green }
            val blue = remember(color) { color.blue }
            RGBConfigurer(red, green, blue) { r, g, b ->
                val newHSL = Color.hslFromRgb(r, g, b)
                hsl.hue = newHSL.hue
                hsl.saturation = newHSL.saturation
                hsl.lightness = newHSL.lightness
            }
        }
        Configurer.HEX -> {
            HexConfigurer(color) { hex ->
                val newHSL = hex.toHSL()
                hsl.hue = newHSL.hue
                hsl.saturation = newHSL.saturation
                hsl.lightness = newHSL.lightness
            }
        }
    }
}

@Composable
private fun HSLConfigurer(hsl: MutableList<Float>) {
    ColorSpaceSlider(title = 'H', value = hsl.hue, range = 0f..360f) { h ->
        hsl.hue = h as Float
    }

    ColorSpaceSlider(title = 'S', value = hsl.saturation, range = 0f..1f) { s ->
        hsl.saturation = s as Float
    }

    ColorSpaceSlider(title = 'L', value = hsl.lightness, range = 0f..1f) { l ->
        hsl.lightness = l as Float
    }
}

@Composable
private fun RGBConfigurer(
    red: Int,
    green: Int,
    blue: Int,
    onConfigure: (Int, Int, Int) -> Unit
) {

    ColorSpaceSlider(title = 'R', value = red, range = 0f..255f) { r ->
        onConfigure(r as Int, green, blue)
    }

    ColorSpaceSlider(title = 'G', value = green, range = 0f..255f) { g ->
        onConfigure(red, g as Int, blue)
    }

    ColorSpaceSlider(title = 'B', value = blue, range = 0f..255f) { b ->
        onConfigure(red, green, b as Int)
    }
}

@Composable
private fun <T : Number> ColorSpaceSlider(
    title: Char,
    value: T,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Number) -> Unit
) {
    val isInt = value is Int
    Row(
        modifier = Modifier
            .padding(top = MaterialTheme.dimensions.paddingDefault)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Slider(
            value = value.toFloat(),
            onValueChange = { if (isInt) onValueChange(it.toInt()) else onValueChange(it) },
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimensions.paddingDefault)
                .weight(0.75f),
            valueRange = range,
            steps = if (value is Int) range.endInclusive.toInt() else 0,
            colors = SliderDefaults.colors(
                activeTrackColor = MaterialTheme.colors.primary,
                inactiveTrackColor = MaterialTheme.colors.surface,
                activeTickColor = MaterialTheme.colors.primary,
                inactiveTickColor = MaterialTheme.colors.surface
            )
        )

        Text(
            text = "$title: ${if (isInt) value else "%.2f".format(value)}",
            modifier = Modifier
                .padding(end = MaterialTheme.dimensions.paddingDefault)
                .weight(0.25f)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HexConfigurer(color: Int, onConfigure: (Int) -> Unit) {
    var hex by remember(color) { mutableStateOf(TextFieldValue(color.toHexColor())) }
    var cursorIndex by remember { mutableStateOf(0) }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = hex.copy(selection = TextRange(cursorIndex)),
        onValueChange = {
            val value = it.text
            if (value.length != 6) {
                var diffIndex = min(hex.text.length, value.length)
                for (i in 0 until diffIndex) {
                    if (hex.text[i] != value[i]) {
                        diffIndex = i
                        break
                    }
                }

                val newHex = it.copy(text = StringBuilder(value).apply {
                                if (length < 6) {
                                    insert(it.selection.start, '0')
                                } else {
                                    if (diffIndex > 5) deleteCharAt(diffIndex)
                                    else deleteCharAt(diffIndex + 1)
                                }
                            }.toString().uppercase(Locale.getDefault()))

                try {
                    onConfigure("#${newHex.text}".toColorInt())
                    hex = newHex
                    cursorIndex = newHex.selection.start
                } catch (ignored: Exception) {
                }
            } else {
                hex = it
                cursorIndex = it.selection.start
            }
        },
        modifier = Modifier
            .padding(top = MaterialTheme.dimensions.paddingDefault)
            .widthIn(min = 1.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Tag,
                modifier = Modifier.iconSystem()
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
    )
}
