package com.abanoub.studynotes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.abanoub.studynotes.domain.model.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    // Insert session to DB
    @Insert
    suspend fun insertSession(session: Session)

    // Delete session from DB
    @Delete
    suspend fun deleteSession(session: Session)

    // Get all sessions from DB
    @Query("SELECT * FROM SESSION")
    fun getAllSessions(): Flow<List<Session>>

    // Get recent sessions for a subject
    @Query("SELECT * FROM SESSION WHERE sessionSubjectId = :subjectId")
    fun getRecentSessionsForSubject(subjectId: Int): Flow<List<Session>>

    // Get total sessions duration
    @Query("SELECT SUM(duration) FROM SESSION")
    fun getTotalSessionsDuration(): Flow<Long>

    // Get total sessions duration for a subject
    @Query("SELECT SUM(duration) FROM SESSION WHERE sessionSubjectId = :subjectId")
    fun getTotalSessionsDurationForSubject(subjectId: Int): Flow<Long>

    @Query("DELETE FROM SESSION WHERE sessionSubjectId = :subjectId")
    fun deleteSessionsBySubjectId(subjectId: Int)

}