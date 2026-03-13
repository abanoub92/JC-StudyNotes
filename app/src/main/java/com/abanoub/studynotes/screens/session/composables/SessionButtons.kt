package com.abanoub.studynotes.screens.session.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abanoub.studynotes.screens.session.TimerState


fun LazyListScope.sessionButtons(
    modifier: Modifier,
    startButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit,
    finishButtonClick: () -> Unit,
    timerState: TimerState,
    seconds: String
){
    item {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                enabled = timerState != TimerState.STARTED && seconds != "00",
                onClick = cancelButtonClick
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    text = "Cancel"
                )
            }

            Button(
                onClick = startButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (timerState == TimerState.STARTED) Color.Red
                    else MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    text = when(timerState){
                        TimerState.STARTED -> "Stop"
                        TimerState.STOPPED -> "Resume"
                        else -> "Start"
                    }
                )
            }

            Button(
                enabled = timerState != TimerState.STARTED && seconds != "00",
                onClick = finishButtonClick
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    text = "Finish"
                )
            }
        }
    }
}