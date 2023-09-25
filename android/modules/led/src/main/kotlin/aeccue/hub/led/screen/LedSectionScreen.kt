package aeccue.hub.led.screen

import aeccue.foundation.android.compose.ext.colorAt
import aeccue.foundation.android.compose.ext.updateColor
import aeccue.foundation.android.compose.theme.dimensions
import aeccue.foundation.android.compose.ui.BackButton
import aeccue.foundation.android.compose.ui.Circle
import aeccue.foundation.android.compose.ui.ExtendedSmoothTopBar
import aeccue.foundation.android.compose.ui.IconButton
import aeccue.foundation.android.navigation.NavRoute
import aeccue.foundation.android.os.ext.getOptionalInt
import aeccue.foundation.android.screen.NavComposableScreen
import aeccue.foundation.dagger.scope.ScreenScope
import aeccue.hub.led.compose.LedLargeToggleButton
import aeccue.hub.led.model.BYTES_PER_LED
import aeccue.hub.led.model.LedGroup
import aeccue.hub.led.model.LedSection
import aeccue.hub.led.viewmodel.LedSectionViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import javax.inject.Inject

private const val ARG_GROUP = "group"
private const val ARG_SETUP_ID = "setupId"
private const val ARG_SECTION_ID = "sectionId"

private const val COLUMN_COUNT = 5

@ScreenScope
class LedSectionScreen @Inject constructor(
    private val viewModel: LedSectionViewModel
) : NavComposableScreen() {

    object Route : NavRoute(
        rootRoute = "led/section",
        arguments = listOf(
            navArgument(ARG_GROUP) { type = NavType.EnumType(LedGroup::class.java) },
            navArgument(ARG_SETUP_ID) { type = NavType.IntType },
            navArgument(ARG_SECTION_ID) { type = NavType.IntType }
        )
    ) {

        fun create(group: LedGroup, setupId: Int, sectionId: Int): String =
            create(arrayOf(group.name, setupId.toString(), sectionId.toString()))

        internal fun getArguments(backStackEntry: NavBackStackEntry): Triple<LedGroup, Int, Int>? =
            backStackEntry.arguments?.run {
                val group = get(ARG_GROUP) as? LedGroup ?: return null
                val setupId = getOptionalInt(ARG_SETUP_ID) ?: return null
                val sectionId = getOptionalInt(ARG_SECTION_ID) ?: return null

                Triple(group, setupId, sectionId)
            }
    }

    override val name = "LED Section"
    override val navRoute = Route

    @Composable
    override fun Screen(navController: NavController, backStackEntry: NavBackStackEntry) {
        Route.getArguments(backStackEntry)?.let { (group, setupId, sectionId) ->
            val section by viewModel.getSection(group, setupId, sectionId)
                .collectAsState(initial = null)

            val ledBuffer = remember(section) {
                mutableStateListOf(
                    *(section?.leds?.toTypedArray() ?: emptyArray())
                )
            }

            val selectedLeds = remember(section) { mutableStateMapOf<Int, Color>() }
            var showColorEditScreen by remember { mutableStateOf(false) }

            section?.let { s ->
                if (showColorEditScreen) {
                    if (selectedLeds.size == 1) {
                        val (index, color) = selectedLeds.entries.first()
                        LedColorEditScreen(
                            initialColor = color,
                            onSet = {
                                ledBuffer.updateColor(index * BYTES_PER_LED, it)
                                selectedLeds.clear()
                                showColorEditScreen = false
                            },
                            onDismiss = {
                                selectedLeds.clear()
                                showColorEditScreen = false
                            }
                        )
                    } else {
                        LedColorRangeEditScreen(
                            initialColorRange = selectedLeds,
                            onSet = {
                                it.forEach { (i, color) ->
                                    ledBuffer.updateColor(i * BYTES_PER_LED, color)
                                }
                                selectedLeds.clear()
                                showColorEditScreen = false
                            },
                            onDismiss = {
                                selectedLeds.clear()
                                showColorEditScreen = false
                            }
                        )
                    }
                } else {
                    SectionScreen(navController, s, ledBuffer, selectedLeds) {
                        showColorEditScreen = true
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun SectionScreen(
        navController: NavController,
        section: LedSection,
        leds: List<Byte>,
        selectedLeds: MutableMap<Int, Color>,
        onColorEdit: () -> Unit
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                section = section,
                onBack = { navController.navigateUp() },
                onUpload = { viewModel.updateSection(section.copy(leds = leds.toByteArray())) },
                onSectionToggle = { viewModel.toggleSection(section, it) }
            )

            Row(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.dimensions.paddingDefault)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    imageVector = Icons.Filled.Edit,
                    enabled = selectedLeds.isNotEmpty()
                ) {
                    onColorEdit()
                }

                IconButton(imageVector = Icons.Filled.Save) {

                }

                IconButton(imageVector = Icons.Filled.Download) {

                }
            }

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
                val rowCount = (section.ledCount + COLUMN_COUNT - 1) / COLUMN_COUNT
                items(rowCount) { row ->
                    Row {
                        val itemsCount =
                            if (row * COLUMN_COUNT + COLUMN_COUNT <= section.ledCount) COLUMN_COUNT
                            else section.ledCount % COLUMN_COUNT

                        for (n in 0 until itemsCount) {
                            val indexLed = row * COLUMN_COUNT + n
                            val color = leds.colorAt(indexLed * BYTES_PER_LED)

                            Circle(
                                color = color,
                                modifier = Modifier
                                    .size(MaterialTheme.dimensions.iconSizeDefault)
                                    .combinedClickable(
                                        onClick = {
                                            when {
                                                selectedLeds.isEmpty() -> {
                                                    selectedLeds[indexLed] = color
                                                    onColorEdit()
                                                }
                                                selectedLeds.containsKey(indexLed) ->
                                                    selectedLeds.remove(indexLed)
                                                else -> selectedLeds[indexLed] = color
                                            }
                                        },
                                        onLongClick = {
                                            if (selectedLeds.isEmpty()) {
                                                selectedLeds[indexLed] = color
                                            } else {
                                                var startIndex = 0
                                                selectedLeds.forEach { (i, _) ->
                                                    if (i in (startIndex + 1) until indexLed) {
                                                        startIndex = i
                                                    }
                                                }
                                                for (i in startIndex..indexLed) {
                                                    selectedLeds[i] =
                                                        leds.colorAt(i * BYTES_PER_LED)
                                                }
                                            }
                                        }
                                    )
                                    .background(
                                        color = if (selectedLeds.containsKey(indexLed)) {
                                            Color.White.copy(alpha = ContentAlpha.medium)
                                        } else {
                                            MaterialTheme.colors.background
                                        }
                                    )
                                    .padding(MaterialTheme.dimensions.paddingIconSystem)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TopBar(
        section: LedSection,
        onBack: () -> Unit,
        onUpload: () -> Unit,
        onSectionToggle: (Boolean) -> Unit
    ) {
        ExtendedSmoothTopBar(content = {
            BackButton(onBack = onBack)

            Spacer(modifier = Modifier.weight(1f))

            IconButton(imageVector = Icons.Filled.Send, onClick = onUpload)
        }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.dimensions.paddingDefault)
            ) {
                LedLargeToggleButton(
                    checked = section.on,
                    onCheckedChange = onSectionToggle
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = MaterialTheme.dimensions.paddingDefault)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = section.name,
                        style = MaterialTheme.typography.h5
                    )

                    Text(
                        text = "LED: ${section.ledCount}",
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    }
}
