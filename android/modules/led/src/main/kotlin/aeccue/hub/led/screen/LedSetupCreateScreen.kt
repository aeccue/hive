package aeccue.hub.led.screen

import aeccue.foundation.android.compose.InputDialog
import aeccue.foundation.android.compose.ext.Icon
import aeccue.foundation.android.compose.ext.iconSystem
import aeccue.foundation.android.compose.shape.Triangle
import aeccue.foundation.android.compose.theme.dimensions
import aeccue.foundation.android.compose.ui.Divider
import aeccue.foundation.android.compose.ui.IconButton
import aeccue.foundation.android.compose.ui.ListSelector
import aeccue.foundation.util.INVALID_VALUE
import aeccue.hub.led.R
import aeccue.hub.led.model.LedGroup
import aeccue.hub.led.model.LedSetup
import aeccue.hub.led.model.LedSetupIcon
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LedSetupCreateScreen(onCreate: (LedSetup) -> Unit, onDismiss: () -> Unit) {
    var group by remember { mutableStateOf(LedGroup.Desk) }
    var id by remember { mutableStateOf(Int.INVALID_VALUE) }
    var name by remember { mutableStateOf("") }
    var icon by remember { mutableStateOf(LedSetupIcon.DeskLamp) }

    var iconSelectorOpened by remember { mutableStateOf(false) }
    var groupSelectorOpened by remember { mutableStateOf(false) }

    InputDialog(
        title = stringResource(R.string.title_new_setup),
        inputTitle = stringResource(R.string.button_create),
        onInput = {
            onCreate(
                LedSetup(
                    group = group,
                    id = id,
                    name = name,
                    icon = icon
                )
            )
        },
        onDismiss = onDismiss
    ) {
        Box(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.paddingDefault)
                .fillMaxWidth()
                .clickable {
                    groupSelectorOpened = !groupSelectorOpened
                },
        ) {
            Text(
                text = group.name.uppercase(Locale.getDefault()),
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
            selected = group,
            modifier = Modifier
                .padding(bottom = MaterialTheme.dimensions.paddingDefault)
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface),
            open = groupSelectorOpened
        ) {
            group = it
            groupSelectorOpened = false
        }

        if (iconSelectorOpened) {
            IconSelector(icon) {
                icon = it
                iconSelectorOpened = false
            }
        }


        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
        ) {
            IconButton(
                imageVector = icon.vector,
                tint = MaterialTheme.colors.onBackground,
                onClick = { iconSelectorOpened = !iconSelectorOpened }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = MaterialTheme.dimensions.paddingSmall)
            ) {
                val idFocusRequester = FocusRequester()

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusOrder {
                            next = idFocusRequester
                        },
                    placeholder = {
                        Text(text = stringResource(R.string.textfield_name))
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
                )

                val keyboardController = LocalSoftwareKeyboardController.current

                TextField(
                    value = if (id < 0) "" else id.toString(),
                    onValueChange = {
                        id = it.toIntOrNull() ?: id
                    },
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.dimensions.paddingDefault,
                        )
                        .fillMaxWidth()
                        .focusOrder(idFocusRequester),
                    placeholder = {
                        Text(text = stringResource(R.string.textfield_id))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ColumnScope.IconSelector(
    currentIcon: LedSetupIcon,
    onIconSelect: (LedSetupIcon) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(MaterialTheme.dimensions.iconSizeSystem),
    ) {
        items(LedSetupIcon.values()) { icon ->
            val iconTint =
                if (currentIcon == icon) MaterialTheme.colors.primary
                else MaterialTheme.colors.primary.copy(alpha = ContentAlpha.medium)
            Icon(
                imageVector = icon.vector,
                tint = iconTint,
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .iconSystem()
                    .clickable { onIconSelect(icon) }
            )
        }
    }

    Box(
        modifier = Modifier
            .align(Alignment.Start)
            .height(MaterialTheme.dimensions.paddingIconSystem)
            .width(MaterialTheme.dimensions.iconSizeSystem)
            .padding(horizontal = MaterialTheme.dimensions.paddingIconSystem)
            .clip(Triangle(Triangle.Direction.DOWN))
            .background(MaterialTheme.colors.surface)
    )
}
