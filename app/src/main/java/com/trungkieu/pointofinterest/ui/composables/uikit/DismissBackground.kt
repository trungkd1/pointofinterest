package com.trungkieu.pointofinterest.ui.composables.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.trungkieu.pointofinterest.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val color = when (dismissState.dismissDirection) {
        // VUốt từ phải qua trái
        DismissDirection.StartToEnd -> Color(0xFFFF1744)
        // VUốt từ trái qua phải
        DismissDirection.EndToStart -> Color(0xFF1DE9B6)
        null -> Color.Transparent
    }
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (direction == DismissDirection.StartToEnd) Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart) Icon(
            // make sure add baseline_archive_24 resource to drawable folder
            painter = painterResource(R.drawable.ic_delete_forever),
            contentDescription = "Archive"
        )
    }
}