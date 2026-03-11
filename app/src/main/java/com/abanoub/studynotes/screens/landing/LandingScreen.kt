package com.abanoub.studynotes.screens.landing

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abanoub.studynotes.screens.components.AddSubjectDialog
import com.abanoub.studynotes.screens.components.DeleteDialog
import com.abanoub.studynotes.screens.landing.composables.LandingCounter
import com.abanoub.studynotes.screens.landing.composables.LandingSubjectCards
import com.abanoub.studynotes.screens.landing.composables.LandingTopBar
import com.abanoub.studynotes.screens.components.studySessionList
import com.abanoub.studynotes.screens.components.taskList
import com.abanoub.studynotes.util.SnackBarEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LandingScreen(
    onSubjectCardClicked: (Int?) -> Unit,
    onTaskCardClicked: (Int?) -> Unit,
    onStartSessionButtonClicked: () -> Unit,
){

    val viewModel = hiltViewModel<LandingViewModel>()
    val scrollState = rememberLazyListState()
    val snackBarHostState = remember { SnackbarHostState() }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val recentSessions by viewModel.sessions.collectAsStateWithLifecycle()

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.snackBarEventFlow.collectLatest { event ->
            when(event){
                is SnackBarEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }

                SnackBarEvent.NavigateUp -> {}
            }
        }
    }

    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = {
            viewModel.onEvent(LandingEvent.SaveSubject)
            isAddSubjectDialogOpen = false
        },
        subjectName = state.subjectName,
        onSubjectNameChange = { viewModel.onEvent(LandingEvent.OnSubjectNameChange(it)) },
        goalHours = state.goalStudyHours,
        onGoalHoursChange = { viewModel.onEvent(LandingEvent.OnGoalStudyHoursChange(it)) },
        selectedColors = state.subjectCardColors,
        onColorChange = { viewModel.onEvent(LandingEvent.OnSubjectCardColorChange(it)) }
    )

    DeleteDialog(
        isOpen = isDeleteDialogOpen,
        title = "Delete Session",
        description = "Are you sure, you want to delete this session? Your study hours will be " +
                "reduced by this session time. This action can not be undo",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButtonClick = {
            viewModel.onEvent(LandingEvent.DeleteSession)
            isDeleteDialogOpen = false
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = { LandingTopBar() }
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(it),
            state = scrollState
        ) {

            item {
                LandingCounter(
                    modifier = Modifier.fillMaxSize()
                        .padding(12.dp),
                    subjectCount = state.totalSubjectCount,
                    studiedHours = state.totalStudyHours.toString(),
                    goalStudyHours = state.totalGoalStudyHours.toString()
                )
            }

            item {
                LandingSubjectCards(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = state.subjects,
                    onAddButtonClicked = { isAddSubjectDialogOpen = true },
                    onSubjectClicked = onSubjectCardClicked
                )
            }

            item {
                Button(
                    onClick = onStartSessionButtonClicked,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text("Start Study Session")
                }
            }

            taskList(
                title = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks.\n" +
                        "Click the + button in subject screen to add new task.",
                taskList = tasks,
                onTaskClicked = onTaskCardClicked,
                onCheckboxClicked = { viewModel.onEvent(LandingEvent.OnTaskIsCompleteChange(it)) }
            )

            item { Spacer(modifier = Modifier.padding(20.dp)) }

            studySessionList(
                title = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any recent study sessions.\n" +
                        "Start a study session to begin recording your progress.",
                sessionList = recentSessions,
                onDeleteSession = {
                    viewModel.onEvent(LandingEvent.OnDeleteSessionButtonClick(it))
                    isDeleteDialogOpen = true
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview(){
    LandingScreen(
        onSubjectCardClicked = {},
        onTaskCardClicked = {},
        onStartSessionButtonClicked = {}
    )
}


