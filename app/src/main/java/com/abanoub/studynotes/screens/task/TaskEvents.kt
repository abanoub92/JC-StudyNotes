package com.abanoub.studynotes.screens.task

import com.abanoub.studynotes.domain.model.Subject
import com.abanoub.studynotes.util.Priority

sealed class TaskEvents {

    data class OnTitleChange(val title: String): TaskEvents()

    data class OnDescriptionChange(val description: String): TaskEvents()

    data class OnDateChange(val millis: Long?): TaskEvents()

    data class OnPriorityChange(val priority: Priority): TaskEvents()

    data class OnRelatedSubjectSelect(val subject: Subject): TaskEvents()

    data object OnIsCompleteChange: TaskEvents()

    data object SaveTask: TaskEvents()

    data object DeleteTask: TaskEvents()

}