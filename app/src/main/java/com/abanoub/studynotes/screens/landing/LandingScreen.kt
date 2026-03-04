package com.abanoub.studynotes.screens.landing

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abanoub.studynotes.domain.model.Subject
import com.abanoub.studynotes.domain.sessions
import com.abanoub.studynotes.domain.subjects
import com.abanoub.studynotes.domain.tasks
import com.abanoub.studynotes.screens.components.AddSubjectDialog
import com.abanoub.studynotes.screens.components.DeleteDialog
import com.abanoub.studynotes.screens.landing.composables.LandingCounter
import com.abanoub.studynotes.screens.landing.composables.LandingSubjectCards
import com.abanoub.studynotes.screens.landing.composables.LandingTopBar
import com.abanoub.studynotes.screens.landing.composables.landingStudySessionList
import com.abanoub.studynotes.screens.landing.composables.landingTaskList

@Composable
fun LandingScreen(){

    val scrollState = rememberLazyListState()

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }

    var subjectName by remember { mutableStateOf("") }
    var goalHours by remember { mutableStateOf("") }
    var selectedColors by remember { mutableStateOf(Subject.subjectCardColors.random()) }

    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = { isAddSubjectDialogOpen = false },
        subjectName = subjectName,
        onSubjectNameChange = { subjectName = it },
        goalHours = goalHours,
        onGoalHoursChange = { goalHours = it },
        selectedColors = selectedColors,
        onColorChange = { selectedColors = it }
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
                    subjectCount = 10,
                    studiedHours = 20,
                    goalStudyHours = 15
                )
            }

            item {
                LandingSubjectCards(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = subjects,
                    onAddButtonClicked = { isAddSubjectDialogOpen = true }
                )
            }

            item {
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text("Start Study Session")
                }
            }

            landingTaskList(
                title = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks.\n" +
                        "Click the + button in subject screen to add new task.",
                taskList = tasks,
                onTaskClicked = {},
                onCheckboxClicked = {}
            )

            item { Spacer(modifier = Modifier.padding(20.dp)) }

            landingStudySessionList(
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
fun LandingScreenPreview(){
    LandingScreen()
}


