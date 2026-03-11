package com.abanoub.studynotes.screens.landing

import androidx.compose.ui.graphics.Color
import com.abanoub.studynotes.domain.model.Session
import com.abanoub.studynotes.domain.model.Task

sealed class LandingEvent {

    data object SaveSubject : LandingEvent()

    data object DeleteSession : LandingEvent()

    data class OnDeleteSessionButtonClick(val session: Session): LandingEvent()

    data class OnTaskIsCompleteChange(val task: Task): LandingEvent()

    data class OnSubjectCardColorChange(val colors: List<Color>): LandingEvent()

    data class OnSubjectNameChange(val name: String): LandingEvent()

    data class OnGoalStudyHoursChange(val hours: String): LandingEvent()

}