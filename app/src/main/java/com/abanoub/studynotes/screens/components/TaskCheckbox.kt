package com.abanoub.studynotes.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abanoub.studynotes.R

@Composable
fun TaskCheckbox(
    isCompleted: Boolean,
    borderColor: Color,
    onCheckboxClicked: () -> Unit
){

    Box(
        modifier = Modifier.size(25.dp)
            .clip(CircleShape)
            .border(2.dp, borderColor, CircleShape)
            .clickable { onCheckboxClicked() },
        contentAlignment = Alignment.Center
    ){
        AnimatedVisibility(isCompleted) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.ic_check),
                tint = borderColor,
                contentDescription = "Completed"
            )
        }
    }

}