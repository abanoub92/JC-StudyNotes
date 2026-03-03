package com.abanoub.studynotes.domain.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val relatedToSubject: String,
    val isCompleted: Boolean,
    val taskSubjectId: Int
)
