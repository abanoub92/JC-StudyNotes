package com.abanoub.studynotes.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.abanoub.studynotes.domain.model.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    // Insert subject if not exist, else update
    @Upsert
    suspend fun upsertSubject(subject: Subject)

    // Get all subjects count
    @Query("SELECT COUNT(*) FROM SUBJECT")
    fun getTotalSubjectsCount(): Flow<Int>

    // Get the summation of all goal hours of all subjects
    @Query("SELECT SUM(goalHours) FROM SUBJECT")
    fun getTotalGoalHours(): Flow<Float>

    // Get specific subject by its id
    @Query("SELECT * FROM SUBJECT WHERE id = :subjectId")
    suspend fun getSubjectById(subjectId: Int): Subject?

    // Delete specific subject by its id
    @Query("DELETE FROM SUBJECT WHERE id = :subjectId")
    suspend fun deleteSubject(subjectId: Int)

    // Get all subjects
    @Query("SELECT * FROM SUBJECT")
    fun getAllSubjects(): Flow<List<Subject>>

}