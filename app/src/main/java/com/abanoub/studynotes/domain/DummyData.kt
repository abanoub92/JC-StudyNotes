package com.abanoub.studynotes.domain

import com.abanoub.studynotes.domain.model.Session
import com.abanoub.studynotes.domain.model.Subject
import com.abanoub.studynotes.domain.model.Task

val subjects = listOf(
    Subject(id = 0, name = "English", goalHours = 10f, colors = Subject.subjectCardColors[0]),
    Subject(id = 0, name = "Physics", goalHours = 10f, colors = Subject.subjectCardColors[1]),
    Subject(id = 0, name = "Maths", goalHours = 10f, colors = Subject.subjectCardColors[2]),
    Subject(id = 0, name = "Geology", goalHours = 10f, colors = Subject.subjectCardColors[3]),
    Subject(id = 0, name = "Fine Arts", goalHours = 10f, colors = Subject.subjectCardColors[4]),
)

val tasks = listOf(
    Task(
        title = "Prepare Notes",
        description = "",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "",
        isCompleted = false,
        id = 1,
        taskSubjectId = 0
    ),
    Task(
        title = "Do Homework",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "",
        isCompleted = true,
        id = 1,
        taskSubjectId = 0
    ),
    Task(
        title = "Go Coaching",
        description = "",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "",
        isCompleted = false,
        id = 1,
        taskSubjectId = 0
    ),
    Task(
        title = "Assignment",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "",
        isCompleted = false,
        id = 1,
        taskSubjectId = 0
    ),
    Task(
        title = "Write Poem",
        description = "",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "",
        isCompleted = true,
        id = 1,
        taskSubjectId = 0
    )
)

val sessions = listOf(
    Session(
        relatedToSubject = "English",
        date = 0L,
        duration = 2L,
        sessionSubjectId = 0,
        id = 0
    ),
    Session(
        relatedToSubject = "Physics",
        date = 0L,
        duration = 2L,
        sessionSubjectId = 0,
        id = 0
    ),
    Session(
        relatedToSubject = "Maths",
        date = 0L,
        duration = 2L,
        sessionSubjectId = 0,
        id = 0
    ),
    Session(
        relatedToSubject = "English",
        date = 0L,
        duration = 2L,
        sessionSubjectId = 0,
        id = 0
    ),
    Session(
        relatedToSubject = "Maths",
        date = 0L,
        duration = 2L,
        sessionSubjectId = 0,
        id = 0
    )
)