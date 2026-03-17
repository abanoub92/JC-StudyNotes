package com.abanoub.studynotes.screens.session

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abanoub.studynotes.screens.components.DeleteDialog
import com.abanoub.studynotes.screens.session.composables.SessionTopBar
import com.abanoub.studynotes.screens.session.composables.sessionTimer
import com.abanoub.studynotes.screens.components.SubjectListBottomSheet
import com.abanoub.studynotes.screens.components.studySessionList
import com.abanoub.studynotes.screens.session.composables.sessionButtons
import com.abanoub.studynotes.screens.session.composables.sessionRelatedToSubject
import com.abanoub.studynotes.util.Constants.ACTION_SERVICE_CANCEL
import com.abanoub.studynotes.util.Constants.ACTION_SERVICE_START
import com.abanoub.studynotes.util.Constants.ACTION_SERVICE_STOP
import com.abanoub.studynotes.util.SnackBarEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen(
    onBackButtonClicked: () -> Unit
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val timerService = LocalTimerService.current

    val viewModel = hiltViewModel<SessionViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val hours by timerService.hours
    val minutes by timerService.minutes
    val seconds by timerService.seconds
    val currentTimerState by timerService.currentTimerState

    var isSubjectBottomSheetOpen by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }

    val snackBarHostState = remember { SnackbarHostState() }

    SubjectListBottomSheet(
        sheetState = bottomSheetState,
        isOpen = isSubjectBottomSheetOpen,
        subjects = state.subjects,
        onSubjectClicked = { subject ->
            viewModel.onEvent(SessionEvent.OnRelatedToSubjectChange(subject))
            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) isSubjectBottomSheetOpen = false
            }
        },
        onDismissRequest = { isSubjectBottomSheetOpen = false }
    )

    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session",
        description = "Are you sure, you want to delete this session? Your study hours will be " +
                "reduced by this session time. This action can not be undo",
        onDismissRequest = { isDeleteSessionDialogOpen = false },
        onConfirmButtonClick = {
            isDeleteSessionDialogOpen = false
            viewModel.onEvent(SessionEvent.DeleteSession)
        }
    )

    LaunchedEffect(true) {
        viewModel.snackBarEventFlow.collectLatest { event ->
            when(event){
                is SnackBarEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }

                SnackBarEvent.NavigateUp -> { onBackButtonClicked() }
            }
        }
    }

    LaunchedEffect(state.subjects) {
        val subjectId = timerService.subjectId.value
        viewModel.onEvent(
            SessionEvent.UpdateSubjectIdAndRelatedToSubject(
                subjectId = subjectId,
                relatedToSubject = state.subjects.find { it.id == subjectId }?.name
            )
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            SessionTopBar(
                onBackButtonClick = onBackButtonClicked
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(it)
        ) {

            sessionTimer(
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(1f),
                hours = hours,
                minutes = minutes,
                seconds = seconds
            )

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }

            sessionRelatedToSubject(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp),
                relatedToSubject = state.relatedToSubject ?: "",
                seconds = seconds,
                onSelectSubjectButtonClick = { isSubjectBottomSheetOpen = true }
            )

            sessionButtons(
                modifier = Modifier.fillMaxWidth()
                    .padding(12.dp),
                startButtonClick = {
                    if (state.subjectId != null && state.relatedToSubject != null) {
                        ServiceHelper.triggerForegroundService(
                            context = context,
                            action = if (currentTimerState == TimerState.STARTED)
                                ACTION_SERVICE_STOP
                            else
                                ACTION_SERVICE_START
                        )
                        timerService.subjectId.value = state.subjectId
                    } else {
                        viewModel.onEvent(SessionEvent.NotifyToUpdateSubject)
                    }
                },
                cancelButtonClick = {
                    ServiceHelper.triggerForegroundService(
                        context = context,
                        action = ACTION_SERVICE_CANCEL
                    )
                },
                finishButtonClick = {
                    val duration = timerService.duration.toLong(DurationUnit.SECONDS)
                    if (duration >= 36){
                        ServiceHelper.triggerForegroundService(
                            context = context,
                            action = ACTION_SERVICE_CANCEL
                        )
                    }

                    viewModel.onEvent(SessionEvent.SaveSession(duration))
                },
                timerState = currentTimerState,
                seconds = seconds
            )

            studySessionList(
                title = "STUDY SESSIONS HISTORY",
                emptyListText = "You don't have any recent study sessions.\n" +
                        "Start a study session to begin recording your progress.",
                sessionList = state.sessions,
                onDeleteSession = { session ->
                    viewModel.onEvent(SessionEvent.OnDeleteSessionButtonClick(session))
                    isDeleteSessionDialogOpen = true
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SessionScreenPreview(){
    SessionScreen(
        onBackButtonClicked = {}
    )
}