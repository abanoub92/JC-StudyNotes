package com.abanoub.studynotes.screens.session

import com.abanoub.studynotes.domain.model.Session
import com.abanoub.studynotes.domain.model.Subject

sealed class SessionEvent {

    data class OnRelatedToSubjectChange(val subject: Subject): SessionEvent()

    data class SaveSession(val duration: Long): SessionEvent()

    data class OnDeleteSessionButtonClick(val session: Session): SessionEvent()

    data class UpdateSubjectIdAndRelatedToSubject(
        val subjectId: Int?,
        val relatedToSubject: String?
    ): SessionEvent()

    data object DeleteSession: SessionEvent()

    data object NotifyToUpdateSubject: SessionEvent()

}
