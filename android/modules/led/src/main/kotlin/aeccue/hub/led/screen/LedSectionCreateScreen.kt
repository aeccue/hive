package aeccue.hub.led.screen

import aeccue.foundation.android.compose.InputDialog
import aeccue.foundation.android.compose.ext.Icon
import aeccue.foundation.android.compose.theme.dimensions
import aeccue.foundation.android.compose.ui.RainbowRing
import aeccue.foundation.util.INVALID_VALUE
import aeccue.hub.led.R
import aeccue.hub.led.model.LedSection
import aeccue.hub.led.model.LedSetup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LedSectionCreateScreen(setup: LedSetup, onCreate: (LedSection) -> Unit, onDismiss: () -> Unit) {
    var id by remember { mutableStateOf(Int.INVALID_VALUE) }
    var name by remember { mutableStateOf("") }
    var ledCount by remember { mutableStateOf(0) }

    InputDialog(
        title = stringResource(R.string.title_new_section),
        inputTitle = stringResource(R.string.button_create),
        onInput = {
            onCreate(
                LedSection(
                    id = id,
                    setupId = setup.id,
                    group = setup.group,
                    name = name,
                    ledCount = ledCount
                )
            )
        },
        onDismiss = onDismiss
    ) {
        SetupDescription(setup = setup)

        val idFocusRequester = FocusRequester()
        val ledCountFocusRequester = FocusRequester()

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

        Row(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.paddingDefault)
                .fillMaxWidth()
        ) {
            TextField(
                value = if (id < 0) "" else id.toString(),
                onValueChange = {
                    id = it.toIntOrNull() ?: id
                },
                modifier = Modifier
                    .padding(top = MaterialTheme.dimensions.paddingDefault)
                    .weight(1f)
                    .focusOrder(
                        focusRequester = idFocusRequester,
                        focusOrderReceiver = {
                            next = ledCountFocusRequester
                        }),
                placeholder = {
                    Text(text = stringResource(R.string.textfield_id))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
            )

            Spacer(modifier = Modifier.width(MaterialTheme.dimensions.paddingDefault))

            val keyboardController = LocalSoftwareKeyboardController.current
            TextField(
                value = if (ledCount <= 0) "" else ledCount.toString(),
                onValueChange = {
                    ledCount = it.toIntOrNull() ?: ledCount
                },
                modifier = Modifier
                    .padding(top = MaterialTheme.dimensions.paddingDefault)
                    .weight(1f)
                    .focusOrder(ledCountFocusRequester),
                placeholder = {
                    Text(text = stringResource(R.string.textfield_led_count))
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

@Composable
private fun SetupDescription(setup: LedSetup) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimensions.paddingDefault)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimensions.paddingDefault)
                .size(MaterialTheme.dimensions.iconLarge)
        ) {
            RainbowRing(modifier = Modifier.fillMaxSize())

            Icon(
                imageVector = setup.icon.vector,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(MaterialTheme.dimensions.iconSizeSystem),
                tint = MaterialTheme.colors.primary
            )
        }

        Column(
            Modifier
                .weight(1f)
                .padding(horizontal = MaterialTheme.dimensions.paddingDefault)
                .align(Alignment.CenterVertically)
        ) {
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
