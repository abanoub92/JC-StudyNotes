package com.abanoub.studynotes.screens.session

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abanoub.studynotes.domain.model.Session
import com.abanoub.studynotes.domain.repository.SessionRepository
import com.abanoub.studynotes.domain.repository.SubjectRepository
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
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class SessionViewModel
@Inject constructor(
    private val sessionRepository: SessionRepository,
    private val subjectRepository: SubjectRepository
): ViewModel() {

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    private val _state = MutableStateFlow(SessionState())
    val state = combine(
        _state,
        subjectRepository.getAllSubjects(),
        sessionRepository.getAllSessions()
    ){ state, subjects, sessions ->
        state.copy(
            subjects = subjects,
            sessions = sessions
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SessionState()
    )

    fun onEvent(event: SessionEvent){
        when(event){
            is SessionEvent.UpdateSubjectIdAndRelatedToSubject -> {
                _state.update {
                    it.copy(
                        subjectId = event.subjectId,
                        relatedToSubject = event.relatedToSubject                    )
                }
            }
            is SessionEvent.OnRelatedToSubjectChange -> {
                _state.update {
                    it.copy(
                        subjectId = event.subject.id,
                        relatedToSubject = event.subject.name
                    )
                }
            }
            is SessionEvent.SaveSession -> saveSession(event.duration)
            is SessionEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(
                        session = event.session
                    )
                }
            }
            SessionEvent.DeleteSession -> deleteSession()
            SessionEvent.NotifyToUpdateSubject -> notifyToUpdateSubject()
        }
    }

    private fun saveSession(duration: Long){
        viewModelScope.launch {
            if (duration < 36){
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Single session can not be less than 36 seconds.",
                        duration = SnackbarDuration.Long
                    )
                )
                return@launch
            }

            try {
                sessionRepository.insertSession(
                    session = Session(
                        sessionSubjectId = _state.value.subjectId ?: -1,
                        relatedToSubject = _state.value.relatedToSubject ?: "",
                        date = Instant.now().toEpochMilli(),
                        duration = duration
                    )
                )
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(message = "Session saved successfully")
                )
            } catch (e: Exception){
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't save session. ${e.message}",
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

    private fun notifyToUpdateSubject(){
        viewModelScope.launch {
            if (_state.value.subjectId == null || _state.value.relatedToSubject == null) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Please select subject related to the session.",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

}