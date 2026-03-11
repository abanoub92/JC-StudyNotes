package com.abanoub.studynotes.screens.subject

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abanoub.studynotes.domain.model.Subject
import com.abanoub.studynotes.domain.repository.SessionRepository
import com.abanoub.studynotes.domain.repository.SubjectRepository
import com.abanoub.studynotes.domain.repository.TaskRepository
import com.abanoub.studynotes.util.SnackBarEvent
import com.abanoub.studynotes.util.toHours
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

@HiltViewModel
class SubjectViewModel
@Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val taskRepository: TaskRepository,
    private val sessionRepository: SessionRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val subjectId: Int = checkNotNull(savedStateHandle["subjectId"]).toString().toInt()

    init {
        fetchSubject()
    }

    private val _state = MutableStateFlow(SubjectState())
    val state = combine(
        _state,
        taskRepository.getUpcomingTasksForSubject(subjectId),
        taskRepository.getCompletedTasksForSubject(subjectId),
        sessionRepository.getRecentTenSessionsForSubject(subjectId),
        sessionRepository.getTotalSessionsDurationForSubject(subjectId)
    ){ state, upcomingTasks, completedTasks, recentSessions, totalSessionsDuration ->
        state.copy(
            upcomingTasks = upcomingTasks,
            completedTasks = completedTasks,
            recentSession = recentSessions,
            studyHours = totalSessionsDuration.toHours()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = SubjectState()
    )

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    fun onEvent(event: SubjectEvent){
        when(event){
            is SubjectEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(subjectName = event.name)
                }
            }
            is SubjectEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(goalStudyHours = event.hours)
                }
            }
            is SubjectEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(subjectCardColors = event.colors)
                }
            }
            is SubjectEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(session = event.session)
                }
            }
            is SubjectEvent.OnTaskIsCompleteChange -> {
                _state.update {
                    it.copy(completedTasks = it.completedTasks + event.task)
                }
            }
            SubjectEvent.UpdateSubject -> { updateSubject() }
            SubjectEvent.DeleteSubject -> { deleteSubject() }
            SubjectEvent.DeleteSession -> { deleteSession() }
            SubjectEvent.UpdateProgress -> {
                val goalStudyHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f
                _state.update {
                    it.copy(
                        progress = (state.value.studyHours / goalStudyHours).coerceIn(0f, 1f)
                    )
                }
            }
        }
    }

    private fun fetchSubject(){
        viewModelScope.launch {
            subjectRepository.getSubjectById(subjectId)?.let { subject ->
                _state.update {
                    it.copy(
                        subjectName = subject.name,
                        goalStudyHours = subject.goalHours.toString(),
                        subjectCardColors = subject.colors.map { Color(it) },
                        currentSubjectId = subject.id
                    )
                }
            }
        }
    }

    private fun updateSubject(){
        viewModelScope.launch {
            try {
                subjectRepository.upsertSubject(
                    subject = Subject(
                        id = _state.value.currentSubjectId,
                        name = _state.value.subjectName,
                        goalHours = _state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                        colors = _state.value.subjectCardColors.map { it.toArgb() }
                    )
                )
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(message = "Subject updated successfully")
                )
            } catch (e: Exception){
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't update subject. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun deleteSubject(){
        viewModelScope.launch {
            try {
                val currentSubjectId = _state.value.currentSubjectId
                if (currentSubjectId != null){
                    withContext(Dispatchers.IO){
                        subjectRepository.deleteSubject(subjectId = currentSubjectId)
                    }

                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(message = "Subject deleted successfully")
                    )
                    _snackBarEventFlow.emit(SnackBarEvent.NavigateUp)
                } else {
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(message = "No Subject to delete")
                    )
                }
            } catch (e: Exception){
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't delete subject. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun deleteSession() {
        viewModelScope.launch {
            try {
                val session = _state.value.session
                if (session != null) {
                    withContext(Dispatchers.IO) {
                        sessionRepository.deleteSession(session = session)
                    }

                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            message = "Session deleted successfully."
                        )
                    )
                } else {
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            message = "No Session to delete."
                        )
                    )
                }
            } catch (e: Exception){
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = e.message ?: "Couldn't delete session. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

}