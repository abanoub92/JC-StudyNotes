package com.abanoub.studynotes.screens.landing

import androidx.compose.ui.graphics.Color
import com.abanoub.studynotes.domain.model.Session
import com.abanoub.studynotes.domain.model.Subject

data class LandingState(
    val totalSubjectCount: Int = 0,
    val totalStudyHours: Float = 0f,
    val totalGoalStudyHours: Float = 0f,
    val subjects: List<Subject> = emptyList(),
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val subjectCardColors: List<Color> = Subject.subjectCardColors.random(),
    val session: Session? = null
)
