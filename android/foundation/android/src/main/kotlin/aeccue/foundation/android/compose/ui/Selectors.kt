package aeccue.foundation.android.compose.ui

import aeccue.foundation.android.compose.theme.dimensions
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import java.util.*

@Composable
inline fun <reified T : Enum<T>> ListSelector(
    selected: T,
    modifier: Modifier = Modifier,
    open: Boolean = true,
    crossinline onSelect: (T) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        if (open) {
            val values = enumValues<T>()
            values.forEachIndexed { i, t ->
                val textColor =
                    if (t == selected) MaterialTheme.colors.onSurface
                    else MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
                Text(
                    text = t.name.uppercase(Locale.getDefault()),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(t) }
                        .padding(vertical = MaterialTheme.dimensions.paddingDefault),
                    color = textColor,
                    textAlign = TextAlign.Center
                )

                if (i != values.size - 1) {
                    Divider(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        width = 0.5f
                    )
                }
            }
        }
    }
}
