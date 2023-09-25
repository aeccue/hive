package aeccue.hub.led.compose

import aeccue.foundation.android.compose.ext.Icon
import aeccue.foundation.android.compose.ext.iconSystem
import aeccue.foundation.android.compose.theme.dimensions
import aeccue.foundation.android.compose.ui.RainbowRing
import aeccue.foundation.android.compose.ui.Ring
import aeccue.hub.led.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun LedToggleButton(checked: Boolean, onCheckedChange: (Boolean) -> Unit, enabled: Boolean = true) {
    IconToggleButton(checked = checked, onCheckedChange = onCheckedChange, enabled = enabled) {
        Icon(
            imageVector = if (checked) Icons.Filled.LightMode else Icons.Outlined.LightMode,
            modifier = Modifier.iconSystem(),
            tint = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun LedLargeToggleButton(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = Modifier
            .padding(horizontal = MaterialTheme.dimensions.paddingDefault)
            .size(MaterialTheme.dimensions.iconLarge)
    ) {
        if (checked) {
            RainbowRing(modifier = Modifier.fillMaxSize())
        } else {
            Ring(
                color = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.medium),
                modifier = Modifier.fillMaxSize()
            )
        }

        val text =
            if (checked) stringResource(R.string.button_on)
            else stringResource(R.string.button_off)
        val textColor =
            if (checked) MaterialTheme.colors.primary
            else MaterialTheme.colors.primary.copy(alpha = ContentAlpha.medium)

        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.h6
        )
    }
}
