package com.abanoub.studynotes.data.repository

import com.abanoub.studynotes.data.local.TaskDao
import com.abanoub.studynotes.domain.model.Task
import com.abanoub.studynotes.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl
@Inject constructor(
    private val taskDao: TaskDao
): TaskRepository {

    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    override suspend fun deleteTasksBySubjectId(subjectId: Int) {
        taskDao.deleteTasksBySubjectId(subjectId)
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }

    override fun getUpcomingTasksForSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getTasksForSubject(subjectId)
            .map { tasks -> tasks.filter { it.isCompleted.not() } }
    }

    override fun getCompletedTasksForSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getAllTasks()
            .map { task -> task.filter { it.isCompleted }
                .also { sortTasks(it) }
            }
    }

    override fun getAllUpcomingTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
            .map { task -> task.filter { it.isCompleted.not() }
                .also { sortTasks(it) }
        }
    }

    private fun sortTasks(taskList: List<Task>): List<Task> {
        return taskList.sortedWith(
            compareBy<Task>{ it.dueDate }.thenBy { it.priority }
        )
    }

}
