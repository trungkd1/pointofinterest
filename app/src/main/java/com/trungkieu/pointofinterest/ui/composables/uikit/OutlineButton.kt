package com.trungkieu.pointofinterest.ui.composables.uikit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OutlineButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    paddingsVertical: Dp = 16.dp,
    paddingsHorizontal: Dp = 48.dp
) {
    val textColor = if (enabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
    val borderColor = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)

    OutlinedButton(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = paddingsHorizontal, vertical = paddingsVertical),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, borderColor),
        enabled = enabled,
        onClick = onClick
    ) {

        Text(text = text.uppercase(), color = textColor, style = MaterialTheme.typography.titleSmall)
    }
}