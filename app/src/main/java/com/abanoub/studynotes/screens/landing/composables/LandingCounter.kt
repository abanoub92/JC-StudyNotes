package com.abanoub.studynotes.screens.landing.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abanoub.studynotes.screens.components.CounterCard

@Composable
fun LandingCounter(
    modifier: Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalStudyHours: String
){
    Row(
        modifier = modifier
    ) {
        CounterCard(
            modifier = Modifier.weight(1f),
            title = "Subject Count",
            value = subjectCount.toString()
        )

        Spacer(modifier = Modifier.width(10.dp))

        CounterCard(
            modifier = Modifier.weight(1f),
            title = "Studied Hours",
            value = studiedHours
        )

        Spacer(modifier = Modifier.width(10.dp))

        CounterCard(
            modifier = Modifier.weight(1f),
            title = "Goal Study Hours",
            value = goalStudyHours
        )
    }
}