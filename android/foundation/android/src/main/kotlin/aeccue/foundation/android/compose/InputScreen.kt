package aeccue.foundation.android.compose

import aeccue.foundation.android.compose.theme.dimensions
import aeccue.foundation.android.compose.ui.BackButton
import aeccue.foundation.android.compose.ui.SmoothTopBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun InputDialog(
    title: String,
    inputTitle: String,
    onInput: () -> Unit,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        InputScreen(title, inputTitle, onInput, onDismiss, content)
    }
}

@Composable
fun InputScreen(
    title: String,
    inputTitle: String,
    onInput: () -> Unit,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        bottomBar = { BottomBar(inputTitle, onInput) },
        topBar = { TopBar(title, onDismiss) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = MaterialTheme.dimensions.paddingDefault,
                    top = it.calculateTopPadding(),
                    end = MaterialTheme.dimensions.paddingDefault,
                    bottom = it.calculateTopPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

@Composable
private fun TopBar(title: String, onDismiss: () -> Unit) {
    SmoothTopBar {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.subtitle1
            )

            BackButton(onBack = onDismiss)
        }
    }
}

@Composable
private fun BottomBar(inputTitle: String, onInput: () -> Unit) {
    BottomAppBar(
        modifier = Modifier.clickable(onClick = onInput),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    ) {
        Text(
            text = inputTitle,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.button
        )
    }
}

