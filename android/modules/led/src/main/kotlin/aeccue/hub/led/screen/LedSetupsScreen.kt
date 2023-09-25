/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.screen

import aeccue.foundation.android.compose.ext.Icon
import aeccue.foundation.android.compose.ext.iconSystem
import aeccue.foundation.android.compose.theme.dimensions
import aeccue.foundation.android.compose.ui.AddButton
import aeccue.foundation.android.compose.ui.Circle
import aeccue.foundation.android.compose.ui.ExtendedSmoothTopBar
import aeccue.foundation.android.compose.ui.RainbowRing
import aeccue.foundation.android.navigation.NavRoute
import aeccue.foundation.android.screen.NavComposableScreen
import aeccue.foundation.dagger.scope.ScreenScope
import aeccue.hub.led.compose.LedToggleButton
import aeccue.hub.led.model.LedGroup
import aeccue.hub.led.model.LedSetup
import aeccue.hub.led.viewmodel.LedSetupsViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject

@ScreenScope
class LedSetupsScreen @Inject constructor(
    private val viewModel: LedSetupsViewModel
) : NavComposableScreen() {

    object Route : NavRoute("led/setups")

    override val name = "LED Setups"
    override val navRoute = Route

    @Composable
    override fun Screen(navController: NavController, backStackEntry: NavBackStackEntry) {
        var showCreateScreen by remember { mutableStateOf(false) }
        if (showCreateScreen) {
            LedSetupCreateScreen(
                onCreate = {
                    viewModel.createSetup(it)
                    showCreateScreen = false
                },
                onDismiss = { showCreateScreen = false }
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                TopBar { showCreateScreen = true }

                val setupList by viewModel.setups.collectAsState(initial = emptyList())
                val setups = LedGroup.values().associateWith { group ->
                    setupList.filter { it.group == group }
                }
                SetupList(
                    setups = setups,
                    getBluetoothStatus = viewModel::bluetoothStatus,
                    onBluetoothConnect = viewModel::connectBluetooth,
                    onSetupSelect = {
                        navController.navigate(
                            LedSetScreen.Route.create(
                                group = it.group,
                                setupId = it.id
                            )
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onSetupToggle = { setup, on ->
                        viewModel.toggleSetup(setup, on)
                    }
                )
            }
        }
    }

    @Composable
    private fun TopBar(onCreateClick: () -> Unit) {
        ExtendedSmoothTopBar(content = {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
            ) {
                AddButton(onAdd = onCreateClick)
            }
        }) {
            Box(
                modifier = Modifier
                    .padding(MaterialTheme.dimensions.paddingDefault)
                    .size(MaterialTheme.dimensions.iconLarge)
                    .align(Alignment.CenterHorizontally)
            ) {
                RainbowRing(modifier = Modifier.fillMaxSize())

                Text(
                    text = LedScreen.NAME,
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }

    @Composable
    private fun ColumnScope.SetupList(
        setups: Map<LedGroup, List<LedSetup>>,
        getBluetoothStatus: (LedGroup) -> StateFlow<Boolean>,
        onBluetoothConnect: (LedGroup) -> Unit,
        onSetupSelect: (LedSetup) -> Unit,
        onSetupToggle: (LedSetup, Boolean) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(MaterialTheme.dimensions.paddingDefault)
        ) {
            setups.forEach { group ->
                val status = getBluetoothStatus(group.key)

                item {
                    val enabled by status.collectAsState()
                    SetupGroupItem(
                        group = group.key,
                        enabled = enabled,
                        onBluetoothConnect = onBluetoothConnect
                    )
                }
                items(group.value) { setup ->
                    val enabled by status.collectAsState()
                    SetupItem(
                        name = setup.name,
                        icon = setup.icon.vector,
                        on = setup.on,
                        enabled = enabled,
                        onSetupSelect = { onSetupSelect(setup) },
                        onSetupToggle = { onSetupToggle(setup, it) })
                }
            }
        }
    }

    @Composable
    private fun SetupGroupItem(
        group: LedGroup,
        enabled: Boolean,
        onBluetoothConnect: (LedGroup) -> Unit
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimensions.listItemHeight)
                .clickable(enabled = !enabled) {
                    onBluetoothConnect(group)
                }
        ) {
            Text(
                text = group.name.uppercase(Locale.getDefault()),
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            Circle(
                color = if (enabled) Color.Green else Color.Red,
                modifier = Modifier
                    .padding(end = MaterialTheme.dimensions.paddingDefault)
                    .size(8.dp)
                    .align(Alignment.CenterEnd)
            )
        }
        Divider(color = MaterialTheme.colors.primary)
    }

    @Composable
    private fun SetupItem(
        name: String,
        icon: ImageVector,
        on: Boolean,
        enabled: Boolean,
        onSetupSelect: () -> Unit,
        onSetupToggle: (Boolean) -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(MaterialTheme.dimensions.listItemHeight)
                .clickable(onClick = onSetupSelect)
        ) {
            Icon(
                imageVector = icon,
                modifier = Modifier.iconSystem(),
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = name,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimensions.paddingDefault)
                    .weight(1f)
            )

            LedToggleButton(
                checked = on,
                onCheckedChange = onSetupToggle,
                enabled = enabled
            )
        }
    }
}
