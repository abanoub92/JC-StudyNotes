package com.abanoub.studynotes.domain.repository

import com.abanoub.studynotes.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {

    suspend fun upsertSubject(subject: Subject)

    fun getTotalSubjectsCount(): Flow<Int>

    fun getTotalGoalHours(): Flow<Float>

    suspend fun getSubjectById(subjectId: Int): Subject?

    suspend fun deleteSubject(subjectId: Int)

    fun getAllSubjects(): Flow<List<Subject>>

}