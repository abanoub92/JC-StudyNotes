package com.abanoub.studynotes.screens.subject

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abanoub.studynotes.R
import com.abanoub.studynotes.screens.components.AddSubjectDialog
import com.abanoub.studynotes.screens.components.DeleteDialog
import com.abanoub.studynotes.screens.components.studySessionList
import com.abanoub.studynotes.screens.components.taskList
import com.abanoub.studynotes.screens.subject.composables.SubjectTopBar
import com.abanoub.studynotes.screens.subject.composables.subjectOverview
import com.abanoub.studynotes.util.SnackBarEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(
    onBackButtonClicked: () -> Unit,
    onAddTaskButtonClicked: (Int?) -> Unit,
    onTaskCardClicked: (Int?, Int?) -> Unit
){

    val viewModel = hiltViewModel<SubjectViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val isFabExtended by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    var isEditSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }

    val snackBarHostState = remember { SnackbarHostState() }


    AddSubjectDialog(
        isOpen = isEditSubjectDialogOpen,
        onDismissRequest = { isEditSubjectDialogOpen = false },
        onConfirmButtonClick = {
            viewModel.onEvent(SubjectEvent.UpdateSubject)
            isEditSubjectDialogOpen = false
        },
        subjectName = state.subjectName,
        onSubjectNameChange = { viewModel.onEvent(SubjectEvent.OnSubjectNameChange(it)) },
        goalHours = state.goalStudyHours,
        onGoalHoursChange = { viewModel.onEvent(SubjectEvent.OnGoalStudyHoursChange(it)) },
        selectedColors = state.subjectCardColors,
        onColorChange = { viewModel.onEvent(SubjectEvent.OnSubjectCardColorChange(it)) }
    )

    DeleteDialog(
        isOpen = isDeleteSubjectDialogOpen,
        title = "Delete Subject",
        description = "Are you sure, you want to delete this subject? All related " +
                "tasks and study sessions will be permanently removed. This action can not be undo",
        onDismissRequest = { isDeleteSubjectDialogOpen = false },
        onConfirmButtonClick = {
            viewModel.onEvent(SubjectEvent.DeleteSubject)
            isDeleteSubjectDialogOpen = false
        }
    )

    DeleteDialog(
        isOpen = isDeleteDialogOpen,
        title = "Delete Session",
        description = "Are you sure, you want to delete this session? Your study hours will be " +
                "reduced by this session time. This action can not be undo",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButtonClick = {
            viewModel.onEvent(SubjectEvent.DeleteSession)
            isDeleteDialogOpen = false
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

    LaunchedEffect(state.studyHours, state.goalStudyHours) {
        viewModel.onEvent(SubjectEvent.UpdateProgress)
    }


    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            SubjectTopBar(
                scrollBehavior = scrollBehavior,
                title = state.subjectName,
                onBackClicked = onBackButtonClicked,
                onEditClicked = { isEditSubjectDialogOpen = true },
                onDeleteClicked = { isDeleteSubjectDialogOpen = true }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = isFabExtended,
                onClick = { onAddTaskButtonClicked(state.currentSubjectId) },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = "Add"
                    )
                },
                text = {
                    Text(text = "Add Task")
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            state = listState
        ) {

            subjectOverview(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                studiedHours = state.studyHours.toString(),
                goalHours = state.goalStudyHours,
                progress = state.progress
            )

            taskList(
                title = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks.\n" +
                        "Click the + button to add new task.",
                taskList = state.upcomingTasks,
                onTaskClicked = { taskId -> onTaskCardClicked(taskId, state.currentSubjectId) },
                onCheckboxClicked = { viewModel.onEvent(SubjectEvent.OnTaskIsCompleteChange(it)) }
            )

            item { Spacer(modifier = Modifier.padding(20.dp)) }

            taskList(
                title = "COMPLETED TASKS",
                emptyListText = "You don't have any completed tasks.\n" +
                        "Click the check box on completion of task.",
                taskList = state.completedTasks,
                onTaskClicked = { taskId -> onTaskCardClicked(taskId, state.currentSubjectId) },
                onCheckboxClicked = { viewModel.onEvent(SubjectEvent.OnTaskIsCompleteChange(it)) }
            )

            item { Spacer(modifier = Modifier.padding(20.dp)) }

            studySessionList(
                title = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any recent study sessions.\n" +
                        "Start a study session to begin recording your progress.",
                sessionList = state.recentSession,
                onDeleteSession = {
                    viewModel.onEvent(SubjectEvent.OnDeleteSessionButtonClick(it))
                    isDeleteDialogOpen = true
                }
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun SubjectScreenPreview(){
    SubjectScreen(
        onBackButtonClicked = {},
        onAddTaskButtonClicked = {},
        onTaskCardClicked = {_, _ ->}
    )
}