package com.abanoub.studynotes.screens.subject.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.abanoub.studynotes.screens.components.CounterCard

fun LazyListScope.subjectOverview(
    modifier: Modifier,
    studiedHours: Int,
    goalHours: Int,
    progress: Float
){

    item {
        val percentageProgress = remember(progress) {
            (progress * 100).toInt().coerceIn(0, 100)
        }

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CounterCard(
                modifier = Modifier.weight(1f),
                title = "Goal Study Hours",
                value = goalHours
            )

            Spacer(modifier = Modifier.width(10.dp))

            CounterCard(
                modifier = Modifier.weight(1f),
                title = "Goal Study Hours",
                value = studiedHours
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier.size(75.dp),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    progress = { 1f },
                    strokeWidth = 4.dp,
                    strokeCap = StrokeCap.Round,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )

                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    progress = { progress },
                    strokeWidth = 4.dp,
                    strokeCap = StrokeCap.Round
                )

                Text(text = "$percentageProgress %")
            }
        }
    }
}