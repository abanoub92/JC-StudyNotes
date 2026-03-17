package com.abanoub.studynotes.screens.session

import com.abanoub.studynotes.domain.model.Session
import com.abanoub.studynotes.domain.model.Subject

data class SessionState(
    val subjects: List<Subject> = emptyList(),
    val sessions: List<Session> = emptyList(),
    val relatedToSubject: String? = null,
    val subjectId: Int? = null,
    val session: Session? = null
)
