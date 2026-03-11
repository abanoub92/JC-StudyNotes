package com.abanoub.studynotes.domain.repository

import com.abanoub.studynotes.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun insertSession(session: Session)

    suspend fun deleteSession(session: Session)

    fun getAllSessions(): Flow<List<Session>>

    fun getRecentTenSessionsForSubject(subjectId: Int): Flow<List<Session>>

    fun getRecentFiveSessions(): Flow<List<Session>>

    fun getTotalSessionsDuration(): Flow<Long>

    fun getTotalSessionsDurationForSubject(subjectId: Int): Flow<Long>

    fun deleteSessionsBySubjectId(subjectId: Int)

}