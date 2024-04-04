package com.trungkieu.pointofinterest.ui.composables.uikit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.background,
    paddingsVertical: Dp = 16.dp,
    paddingsHorizontal: Dp = 24.dp,
    fontSize: TextUnit = 14.sp
) {
    val textColor = if (enabled) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
    Button(
        modifier = modifier.clip(RoundedCornerShape(8.dp)),
        contentPadding = PaddingValues(horizontal = paddingsHorizontal, vertical = paddingsVertical),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = containerColor,
            disabledContainerColor = containerColor,
            disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
        onClick = onClick
    ) {

        Text(text = text.uppercase(), color = textColor, style = MaterialTheme.typography.titleSmall, fontSize = fontSize)
    }
}