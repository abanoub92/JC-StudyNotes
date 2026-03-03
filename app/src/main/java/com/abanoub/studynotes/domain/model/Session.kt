package com.abanoub.studynotes.domain.model

data class Session(
    val sessionSubjectId: Int,
    val relatedToSubject: String,
    val date: Long,
    val duration: Long,
    val id: Int
)
