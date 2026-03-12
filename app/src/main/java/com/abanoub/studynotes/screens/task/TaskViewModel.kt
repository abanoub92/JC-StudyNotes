package com.abanoub.studynotes.screens.task

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abanoub.studynotes.domain.model.Task
import com.abanoub.studynotes.domain.repository.SubjectRepository
import com.abanoub.studynotes.domain.repository.TaskRepository
import com.abanoub.studynotes.util.Priority
import com.abanoub.studynotes.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import java.time.Instant

@HiltViewModel
class TaskViewModel
@Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val taskId: Int? = savedStateHandle.get<String?>("taskId")?.toIntOrNull()
    private val subjectId: Int? = savedStateHandle.get<String?>("subjectId")?.toIntOrNull()

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    private val _state = MutableStateFlow(TaskState())
    val state = combine(
        _state,
        subjectRepository.getAllSubjects()
    ){ state, subjects ->
        state.copy(subjects = subjects)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TaskState()
    )

    init {
        fetchTask()
        fetchSubject()
    }

    fun onEvent(event: TaskEvents){
        when (event){
            is TaskEvents.OnDateChange -> {
                _state.update {
                    it.copy(dueDate = event.millis)
                }
            }
            is TaskEvents.OnDescriptionChange -> {
                _state.update {
                    it.copy(description = event.description)
                }
            }
            is TaskEvents.OnPriorityChange -> {
                _state.update {
                    it.copy(priority = event.priority)
                }
            }
            is TaskEvents.OnRelatedSubjectSelect -> {
                _state.update {
                    it.copy(
                        relatedToSubject = event.subject.name,
                        subjectId = event.subject.id
                    )
                }
            }
            is TaskEvents.OnTitleChange -> {
                _state.update {
                    it.copy(title = event.title)
                }
            }
            TaskEvents.OnIsCompleteChange -> {
                _state.update {
                    it.copy(isTaskComplete = !it.isTaskComplete)
                }
            }
            TaskEvents.DeleteTask -> deleteTask()
            TaskEvents.SaveTask -> saveTask()
        }
    }

    private fun saveTask(){
        viewModelScope.launch {
            try {
                if (_state.value.subjectId == null || _state.value.relatedToSubject == null){
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            message = "Please select subject related to the task",
                            duration = SnackbarDuration.Long
                        )
                    )
                    return@launch
                }

                taskRepository.upsertTask(
                    Task(
                        title = _state.value.title,
                        description = _state.value.description,
                        dueDate = _state.value.dueDate ?: Instant.now().toEpochMilli(),
                        priority = _state.value.priority.value,
                        relatedToSubject = _state.value.relatedToSubject!!,
                        isCompleted = _state.value.isTaskComplete,
                        taskSubjectId = _state.value.subjectId!!,
                        id = _state.value.currentTaskId
                    )
                )

                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Task ${if (_state.value.currentTaskId == null) "saved" else "updated"} successfully")
                )

                _snackBarEventFlow.emit(SnackBarEvent.NavigateUp)
            } catch (e: Exception){
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't update task. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun deleteTask(){
        viewModelScope.launch {
            try {
                val taskId = _state.value.currentTaskId
                if (taskId != null) {
                    withContext(Dispatchers.IO) {
                        taskRepository.deleteTask(taskId = taskId)
                    }

                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            message = "Task deleted successfully."
                        )
                    )

                    _snackBarEventFlow.emit(SnackBarEvent.NavigateUp)
                } else {
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            message = "No task to delete.",
                            duration = SnackbarDuration.Long
                        )
                    )
                }
            } catch (e: Exception){
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = e.message ?: "Couldn't delete task. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun fetchTask(){
        viewModelScope.launch {
            taskId.takeIf { it != -1 }?.let { id ->
                taskRepository.getTaskById(id)?.let { task ->
                    _state.update {
                        it.copy(
                            title = task.title,
                            description = task.description,
                            dueDate = task.dueDate,
                            isTaskComplete = task.isCompleted,
                            priority = Priority.fromInt(task.priority),
                            relatedToSubject = task.relatedToSubject,
                            currentTaskId = task.id
                        )
                    }
                }
            }
        }
    }

    private fun fetchSubject(){
        viewModelScope.launch {
            subjectId?.let { id ->
                subjectRepository.getSubjectById(id)?.let { subject ->
                    _state.update {
                        it.copy(
                            subjectId = subject.id,
                            relatedToSubject = subject.name
                        )
                    }
                }
            }
        }
    }
}