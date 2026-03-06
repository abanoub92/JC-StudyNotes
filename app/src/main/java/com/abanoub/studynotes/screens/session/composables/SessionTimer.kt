package com.abanoub.studynotes.screens.session.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun LazyListScope.sessionTimer(
    modifier: Modifier
){
    item {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(250.dp)
                    .border(
                        width = 5.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape
                    )
            )

            Text(
                text = "00:05:32",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 45.sp
                )
            )
        }
    }
}