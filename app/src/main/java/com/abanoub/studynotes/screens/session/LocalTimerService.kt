package com.abanoub.studynotes.screens.session

import androidx.compose.runtime.compositionLocalOf

val LocalTimerService = compositionLocalOf<StudySessionTimerService> {
    error("No TimerService provided")
}