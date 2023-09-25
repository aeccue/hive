package aeccue.hub.led.screen

import aeccue.foundation.android.compose.ColorPickerScreen
import aeccue.foundation.android.compose.InputDialog
import aeccue.foundation.android.compose.ext.Icon
import aeccue.foundation.android.compose.ext.iconSystem
import aeccue.foundation.android.compose.theme.dimensions
import aeccue.foundation.android.compose.ui.AddButton
import aeccue.foundation.android.compose.ui.Circle
import aeccue.foundation.android.compose.ui.Divider
import aeccue.foundation.android.compose.ui.ListSelector
import aeccue.foundation.util.INVALID_VALUE
import aeccue.hub.led.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import java.util.*

private const val COLUMN_COUNT = 5

private enum class LedColorRangeEditMode {
    Gradient
}

@Composable
fun LedColorRangeEditScreen(
    initialColorRange: Map<Int, Color>,
    onSet: (Map<Int, Color>) -> Unit,
    onDismiss: () -> Unit
) {
    var mode by remember { mutableStateOf(LedColorRangeEditMode.Gradient) }
    val colorRange = remember(initialColorRange) {
        mutableMapOf<Int, Color>().apply {
            putAll(initialColorRange.toSortedMap())
        }
    }
    var modeSelectorOpened by remember { mutableStateOf(false) }

    InputDialog(
        title = stringResource(R.string.title_edit_color_range),
        inputTitle = stringResource(R.string.button_set),
        onInput = { onSet(colorRange) },
        onDismiss = onDismiss
    ) {
        Box(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.paddingDefault)
                .fillMaxWidth()
                .clickable {
                    modeSelectorOpened = !modeSelectorOpened
                },
        ) {
            Text(
                text = mode.name.uppercase(Locale.getDefault()),
                modifier = Modifier
                    .align(Alignment.Center),
                style = MaterialTheme.typography.h6
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                modifier = Modifier
                    .iconSystem()
                    .align(Alignment.CenterEnd)
            )
        }

        Divider(color = MaterialTheme.colors.onBackground)

        ListSelector(
            selected = mode,
            modifier = Modifier
                .padding(bottom = MaterialTheme.dimensions.paddingDefault)
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface),
            open = modeSelectorOpened
        ) {
            mode = it
            modeSelectorOpened = false
        }

        when (mode) {
            LedColorRangeEditMode.Gradient -> GradientMode(colorRange)
        }
    }
}

@Composable
private fun ColumnScope.GradientMode(colorRange: MutableMap<Int, Color>) {
    var colorPickerOpened by remember { mutableStateOf(false) }
    var colorPickerIndex by remember { mutableStateOf(Int.INVALID_VALUE) }
    val gradient = remember { mutableStateListOf<Int>() }

    if (colorPickerOpened) {
        val colorPickerColor = if (colorPickerIndex == Int.INVALID_VALUE) {
            Color.Black.toArgb()
        } else {
            gradient[colorPickerIndex]
        }
        ColorPickerScreen(
            initialColor = Color(colorPickerColor),
            title = stringResource(R.string.title_pick_color),
            onColorPick = {
                if (colorPickerIndex == Int.INVALID_VALUE) {
                    gradient.add(it.toArgb())
                } else {
                    gradient[colorPickerIndex] = it.toArgb()
                }

                val gradientStep = if (gradient.size == 1) 1f else 1f / (gradient.size - 1f)
                val colorStep = 1f / (colorRange.size - 1f)

                var currentGradient = 0
                var currentGradientStep = 0f
                colorRange.entries.forEach { (i, _) ->
                    currentGradientStep += colorStep

                    if (currentGradientStep > gradientStep) {
                        currentGradient++
                        currentGradientStep -= gradientStep
                    }

                    if (currentGradient >= gradient.size - 1) {
                        colorRange[i] = Color(gradient[gradient.size - 1])
                    } else {
                        val currentStep = currentGradientStep / gradientStep
                        colorRange[i] = Color(
                            red = (gradient[currentGradient].red + currentStep * (gradient[currentGradient + 1].red - gradient[currentGradient].red)).toInt(),
                            green = (gradient[currentGradient].green + currentStep * (gradient[currentGradient + 1].green - gradient[currentGradient].green)).toInt(),
                            blue = (gradient[currentGradient].blue + currentStep * (gradient[currentGradient + 1].blue - gradient[currentGradient].blue)).toInt(),
                        )
                    }
                }

                colorPickerOpened = false
            },
            onDismiss = { colorPickerOpened = false },
            modifier = Modifier.fillMaxSize()
        )
    } else {
        GradientColorList(gradient) {
            colorPickerIndex = it
            colorPickerOpened = true
        }
    }
}

@Composable
private fun ColumnScope.GradientColorList(gradient: List<Int>, onClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .wrapContentWidth()
            .weight(1f)
            .padding(
                start = MaterialTheme.dimensions.paddingDefault,
                end = MaterialTheme.dimensions.paddingDefault,
                bottom = MaterialTheme.dimensions.paddingDefault
            )
    ) {
        val rowCount = (gradient.size + COLUMN_COUNT) / COLUMN_COUNT
        items(rowCount) { row ->
            Row {
                val itemsCount =
                    if (row * COLUMN_COUNT + COLUMN_COUNT <= gradient.size + 1) COLUMN_COUNT
                    else (gradient.size + 1) % COLUMN_COUNT

                for (n in 0 until itemsCount) {
                    val index = row * COLUMN_COUNT + n

                    if (index == gradient.size) {
                        AddButton(
                            onAdd = { onClick(Int.INVALID_VALUE) },
                            modifier = Modifier
                                .size(MaterialTheme.dimensions.iconSizeDefault)
                                .padding(MaterialTheme.dimensions.paddingIconSystem)
                                .border(
                                    width = MaterialTheme.dimensions.borderWidth,
                                    color = MaterialTheme.colors.primary,
                                    shape = CircleShape
                                )
                        )
                    } else {
                        Circle(
                            color = Color(gradient[index]),
                            modifier = Modifier
                                .size(MaterialTheme.dimensions.iconSizeDefault)
                                .clickable { onClick(index) }
                                .padding(MaterialTheme.dimensions.paddingIconSystem)
                        )
                    }
                }
            }
        }
    }
}

