package com.abanoub.studynotes.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.abanoub.studynotes.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Insert task if not exist, else update
    @Upsert
    suspend fun upsertTask(task: Task)

    // Delete specific task by its id
    @Query("DELETE FROM TASK WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

    // Delete all tasks bound with specific subject by subject id
    @Query("DELETE FROM TASK WHERE taskSubjectId = :subjectId")
    suspend fun deleteTasksBySubjectId(subjectId: Int)

    // Get specific task by its id
    @Query("SELECT * FROM TASK WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    // Get tasks for specific subject
    @Query("SELECT * FROM TASK WHERE taskSubjectId = :subjectId")
    fun getTasksForSubject(subjectId: Int): Flow<List<Task>>

    // Get all tasks
    @Query("SELECT * FROM TASK")
    fun getAllTasks(): Flow<List<Task>>

}