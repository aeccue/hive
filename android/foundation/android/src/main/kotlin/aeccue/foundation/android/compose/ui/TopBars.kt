package aeccue.foundation.android.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SmoothTopBar(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        content = content
    )
}

@Composable
fun ExtendedSmoothTopBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
    extendedContent: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SmoothTopBar(content = content)

        extendedContent()
    }
}
