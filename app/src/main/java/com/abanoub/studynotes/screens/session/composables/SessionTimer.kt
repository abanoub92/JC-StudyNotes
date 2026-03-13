package com.abanoub.studynotes.screens.session.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
    modifier: Modifier,
    hours: String,
    minutes: String,
    seconds: String
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

            Row() {
                AnimatedContent(
                    targetState = hours,
                    label = "hours",
                    transitionSpec = { timerTextAnimation() }
                ) { h ->
                    Text(
                        text = "$h:",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 45.sp
                        )
                    )
                }

                AnimatedContent(
                    targetState = minutes,
                    label = "minutes",
                    transitionSpec = { timerTextAnimation() }
                ) { m ->
                    Text(
                        text = "$m:",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 45.sp
                        )
                    )
                }

                AnimatedContent(
                    targetState = seconds,
                    label = "seconds",
                    transitionSpec = { timerTextAnimation() }
                ) { s ->
                    Text(
                        text = s,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 45.sp
                        )
                    )
                }
            }

        }
    }
}

private fun timerTextAnimation(duration: Int = 600): ContentTransform {
    return slideInVertically(animationSpec = tween(duration)){ fullHeight -> fullHeight }
        .plus(fadeIn(animationSpec = tween(duration))) togetherWith
            slideOutVertically(animationSpec = tween(duration)){ fullHeight -> -fullHeight }
                .plus(fadeOut(animationSpec = tween(duration)))
}