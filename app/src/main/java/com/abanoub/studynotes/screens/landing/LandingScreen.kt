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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abanoub.studynotes.domain.sessions
import com.abanoub.studynotes.domain.subjects
import com.abanoub.studynotes.domain.tasks
import com.abanoub.studynotes.screens.landing.composables.LandingCounter
import com.abanoub.studynotes.screens.landing.composables.LandingSubjectCards
import com.abanoub.studynotes.screens.landing.composables.LandingTopBar
import com.abanoub.studynotes.screens.landing.composables.landingStudySessionList
import com.abanoub.studynotes.screens.landing.composables.landingTaskList

@Composable
fun LandingScreen(){

    val scrollState = rememberLazyListState()

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
                    subjectList = subjects
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
                onDeleteSession = {}
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview(){
    LandingScreen()
}


