/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.screen

import aeccue.foundation.android.compose.ext.Icon
import aeccue.foundation.android.compose.theme.dimensions
import aeccue.foundation.android.compose.ui.AddButton
import aeccue.foundation.android.compose.ui.BackButton
import aeccue.foundation.android.compose.ui.ExtendedSmoothTopBar
import aeccue.foundation.android.navigation.NavRoute
import aeccue.foundation.android.os.ext.getOptionalInt
import aeccue.foundation.android.screen.NavComposableScreen
import aeccue.foundation.dagger.scope.ScreenScope
import aeccue.hub.led.compose.LedLargeToggleButton
import aeccue.hub.led.compose.LedToggleButton
import aeccue.hub.led.model.LedGroup
import aeccue.hub.led.model.LedSection
import aeccue.hub.led.model.LedSet
import aeccue.hub.led.model.LedSetup
import aeccue.hub.led.viewmodel.LedSetViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import java.util.*
import javax.inject.Inject

private const val ARG_GROUP = "group"
private const val ARG_SETUP_ID = "setupId"

@ScreenScope
class LedSetScreen @Inject constructor(
    private val viewModel: LedSetViewModel
) : NavComposableScreen() {

    object Route : NavRoute(
        rootRoute = "led/setup",
        arguments = listOf(
            navArgument(ARG_GROUP) { type = NavType.EnumType(LedGroup::class.java) },
            navArgument(ARG_SETUP_ID) { type = NavType.IntType }
        )
    ) {

        fun create(group: LedGroup, setupId: Int): String =
            create(arrayOf(group.name, setupId.toString()))

        internal fun getArguments(backStackEntry: NavBackStackEntry): Pair<LedGroup, Int>? =
            backStackEntry.arguments?.run {
                val group = get(ARG_GROUP) as? LedGroup ?: return null
                val setupId = getOptionalInt(ARG_SETUP_ID) ?: return null
                return Pair(group, setupId)
            }
    }

    override val name = "LED Set"
    override val navRoute = Route

    @Composable
    override fun Screen(navController: NavController, backStackEntry: NavBackStackEntry) {
        Route.getArguments(backStackEntry)?.let { (group, setupId) ->
            val set by viewModel.getAllSections(group, setupId).collectAsState(initial = null)
            set?.let { SetScreen(navController, it) }
        }
    }

    @Composable
    private fun SetScreen(navController: NavController, set: LedSet) {
        var showCreateScreen by remember { mutableStateOf(false) }

        if (showCreateScreen) {
            LedSectionCreateScreen(
                setup = set.setup,
                onCreate = {
                    viewModel.createSection(it)
                    showCreateScreen = false
                },
                onDismiss = { showCreateScreen = false }
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                TopBar(
                    setup = set.setup,
                    onBack = { navController.navigateUp() },
                    onCreateClick = { showCreateScreen = true },
                    onSetupToggle = { viewModel.toggleSet(set, it) }
                )

                SectionList(
                    sections = set.sections,
                    onSectionSelect = {
                        navController.navigate(
                            LedSectionScreen.Route.create(
                                group = it.group,
                                setupId = it.setupId,
                                sectionId = it.id
                            )
                        )
                    },
                    onSectionToggle = { section, on ->
                        viewModel.toggleSection(section, on)
                    }
                )
            }
        }
    }

    @Composable
    private fun TopBar(
        setup: LedSetup,
        onBack: () -> Unit,
        onCreateClick: () -> Unit,
        onSetupToggle: (Boolean) -> Unit
    ) {
        ExtendedSmoothTopBar(content = {
            BackButton(onBack = onBack)

            Spacer(modifier = Modifier.weight(1f))

            AddButton(onAdd = onCreateClick)
        }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.dimensions.paddingDefault)
            ) {
                LedLargeToggleButton(
                    checked = setup.on,
                    onCheckedChange = onSetupToggle
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = MaterialTheme.dimensions.paddingDefault)
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = setup.icon.vector,
                        modifier = Modifier.size(MaterialTheme.dimensions.iconSizeSystemNoPadding)
                    )

                    Text(
                        text = setup.name,
                        style = MaterialTheme.typography.h5
                    )

                    Text(
                        text = setup.group.name.uppercase(Locale.getDefault()),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    }

    @Composable
    private fun ColumnScope.SectionList(
        sections: List<LedSection>,
        onSectionSelect: (LedSection) -> Unit,
        onSectionToggle: (LedSection, Boolean) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(MaterialTheme.dimensions.paddingDefault)
        ) {
            items(sections) { section ->
                SectionItem(
                    name = section.name,
                    on = section.on,
                    onSectionSelect = { onSectionSelect(section) },
                    onSectionToggle = { onSectionToggle(section, it) }
                )
            }
        }
    }

    @Composable
    private fun SectionItem(
        name: String,
        on: Boolean,
        onSectionSelect: () -> Unit,
        onSectionToggle: (Boolean) -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(MaterialTheme.dimensions.listItemHeight)
                .clickable(onClick = onSectionSelect)
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimensions.paddingDefault)
                    .weight(1f)
            )


            LedToggleButton(checked = on, onCheckedChange = onSectionToggle)
        }
    }
}
