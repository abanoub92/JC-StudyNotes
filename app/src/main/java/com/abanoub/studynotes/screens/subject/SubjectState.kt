package com.abanoub.studynotes.screens.subject

import androidx.compose.ui.graphics.Color
import com.abanoub.studynotes.domain.model.Session
import com.abanoub.studynotes.domain.model.Subject
import com.abanoub.studynotes.domain.model.Task

data class SubjectState(
    val currentSubjectId: Int? = null,
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val studyHours: Float = 0f,
    val subjectCardColors: List<Color> = Subject.subjectCardColors.random(),
    val recentSession: List<Session> = emptyList(),
    val upcomingTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList(),
    val session: Session? = null,
    val progress: Float = 0f
)