package com.abanoub.studynotes.data.repository

import com.abanoub.studynotes.data.local.SessionDao
import com.abanoub.studynotes.domain.model.Session
import com.abanoub.studynotes.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl
@Inject constructor(
    private val sessionDao: SessionDao
): SessionRepository {

    override suspend fun insertSession(session: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSession(session: Session) {
        TODO("Not yet implemented")
    }

    override fun getAllSessions(): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getRecentSessionsForSubject(subjectId: Int): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionsDuration(): Flow<Long> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionsDurationForSubject(subjectId: Int): Flow<Long> {
        TODO("Not yet implemented")
    }

    override fun deleteSessionsBySubjectId(subjectId: Int) {
        TODO("Not yet implemented")
    }

}