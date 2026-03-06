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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abanoub.studynotes.domain.sessions
import com.abanoub.studynotes.domain.subjects
import com.abanoub.studynotes.screens.components.DeleteDialog
import com.abanoub.studynotes.screens.session.composables.SessionTopBar
import com.abanoub.studynotes.screens.session.composables.sessionTimer
import com.abanoub.studynotes.screens.components.SubjectListBottomSheet
import com.abanoub.studynotes.screens.components.studySessionList
import com.abanoub.studynotes.screens.session.composables.sessionButtons
import com.abanoub.studynotes.screens.session.composables.sessionRelatedToSubject
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen() {

    val scope = rememberCoroutineScope()

    var isSubjectBottomSheetOpen by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    var relatedToSubject by remember { mutableStateOf("") }

    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }

    SubjectListBottomSheet(
        sheetState = bottomSheetState,
        isOpen = isSubjectBottomSheetOpen,
        subjects = subjects,
        onSubjectClicked = {
            relatedToSubject = it.name
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
        onConfirmButtonClick = { isDeleteSessionDialogOpen = false }
    )


    Scaffold(
        topBar = {
            SessionTopBar(
                onBackButtonClick = {}
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(it)
        ) {

            sessionTimer(
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(1f)
            )

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }

            sessionRelatedToSubject(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp),
                relatedToSubject = relatedToSubject,
                onSelectSubjectButtonClick = { isSubjectBottomSheetOpen = true }
            )

            sessionButtons(
                modifier = Modifier.fillMaxWidth()
                    .padding(12.dp),
                startButtonClick = {},
                cancelButtonClick = {},
                finishButtonClick = {}
            )

            studySessionList(
                title = "STUDY SESSIONS HISTORY",
                emptyListText = "You don't have any recent study sessions.\n" +
                        "Start a study session to begin recording your progress.",
                sessionList = sessions,
                onDeleteSession = { isDeleteSessionDialogOpen = true }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SessionScreenPreview(){
    SessionScreen()
}