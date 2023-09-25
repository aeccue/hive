package aeccue.foundation.android.compose.ext

import aeccue.foundation.android.compose.theme.dimensions
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.iconSystem() = this
    .size(MaterialTheme.dimensions.iconSizeSystem)
    .padding(MaterialTheme.dimensions.paddingIconSystem)
