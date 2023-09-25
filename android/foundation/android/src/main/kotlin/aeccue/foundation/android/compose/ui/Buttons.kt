package aeccue.foundation.android.compose.ui

import aeccue.foundation.android.compose.ext.Icon
import aeccue.foundation.android.compose.ext.iconSystem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick, modifier = modifier, enabled = enabled) {
        Icon(
            imageVector = imageVector,
            modifier = Modifier.iconSystem(),
            tint = if (enabled) tint else tint.copy(alpha = ContentAlpha.medium)
        )
    }
}

@Composable
fun AddButton(
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true
) {
    IconButton(
        imageVector = Icons.Filled.Add,
        modifier = modifier,
        tint = tint,
        enabled,
        onClick = onAdd
    )
}

@Composable
fun BackButton(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true
) {
    IconButton(
        imageVector = Icons.Filled.ArrowBack,
        modifier = modifier,
        tint = tint,
        enabled,
        onClick = onBack
    )
}
