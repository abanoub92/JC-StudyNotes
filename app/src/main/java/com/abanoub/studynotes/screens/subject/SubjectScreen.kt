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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.abanoub.studynotes.R
import com.abanoub.studynotes.domain.model.Subject
import com.abanoub.studynotes.domain.sessions
import com.abanoub.studynotes.domain.tasks
import com.abanoub.studynotes.screens.components.AddSubjectDialog
import com.abanoub.studynotes.screens.components.DeleteDialog
import com.abanoub.studynotes.screens.components.studySessionList
import com.abanoub.studynotes.screens.components.taskList
import com.abanoub.studynotes.screens.subject.composables.SubjectTopBar
import com.abanoub.studynotes.screens.subject.composables.subjectOverview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(){

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val isFabExtended by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    var isEditSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }

    var subjectName by remember { mutableStateOf("") }
    var goalHours by remember { mutableStateOf("") }
    var selectedColors by remember { mutableStateOf(Subject.subjectCardColors.random()) }

    AddSubjectDialog(
        isOpen = isEditSubjectDialogOpen,
        onDismissRequest = { isEditSubjectDialogOpen = false },
        onConfirmButtonClick = { isEditSubjectDialogOpen = false },
        subjectName = subjectName,
        onSubjectNameChange = { subjectName = it },
        goalHours = goalHours,
        onGoalHoursChange = { goalHours = it },
        selectedColors = selectedColors,
        onColorChange = { selectedColors = it }
    )

    DeleteDialog(
        isOpen = isDeleteSubjectDialogOpen,
        title = "Delete Subject",
        description = "Are you sure, you want to delete this subject? All related " +
                "tasks and study sessions will be permanently removed. This action can not be undo",
        onDismissRequest = { isDeleteSubjectDialogOpen = false },
        onConfirmButtonClick = { isDeleteSubjectDialogOpen = false }
    )

    DeleteDialog(
        isOpen = isDeleteDialogOpen,
        title = "Delete Session",
        description = "Are you sure, you want to delete this session? Your study hours will be " +
                "reduced by this session time. This action can not be undo",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButtonClick = { isDeleteDialogOpen = false }
    )


    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            SubjectTopBar(
                scrollBehavior = scrollBehavior,
                title = "English",
                onBackClicked = {},
                onEditClicked = { isEditSubjectDialogOpen = true },
                onDeleteClicked = { isDeleteSubjectDialogOpen = true }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = isFabExtended,
                onClick = {  },
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
            modifier = Modifier.fillMaxSize()
                .padding(it),
            state = listState
        ) {

            subjectOverview(
                modifier = Modifier.fillMaxWidth()
                    .padding(12.dp),
                studiedHours = 10,
                goalHours = 15,
                progress = 0.75f
            )

            taskList(
                title = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks.\n" +
                        "Click the + button to add new task.",
                taskList = tasks,
                onTaskClicked = {},
                onCheckboxClicked = {}
            )

            item { Spacer(modifier = Modifier.padding(20.dp)) }

            taskList(
                title = "COMPLETED TASKS",
                emptyListText = "You don't have any completed tasks.\n" +
                        "Click the check box on completion of task.",
                taskList = tasks,
                onTaskClicked = {},
                onCheckboxClicked = {}
            )

            item { Spacer(modifier = Modifier.padding(20.dp)) }

            studySessionList(
                title = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any recent study sessions.\n" +
                        "Start a study session to begin recording your progress.",
                sessionList = sessions,
                onDeleteSession = { isDeleteDialogOpen = true }
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun SubjectScreenPreview(){
    SubjectScreen()
}