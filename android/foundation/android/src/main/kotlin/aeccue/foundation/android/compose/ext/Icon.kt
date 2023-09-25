/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.compose.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier? = null,
    tint: Color? = null
) {
    if (tint == null) {
        androidx.compose.material.Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = modifier ?: Modifier
        )
    } else {
        androidx.compose.material.Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = modifier ?: Modifier,
            tint = tint
        )
    }
}
