package com.abanoub.studynotes.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abanoub.studynotes.screens.task.composables.TaskTopBar
import com.abanoub.studynotes.R
import com.abanoub.studynotes.screens.components.DeleteDialog
import com.abanoub.studynotes.screens.task.composables.PriorityButton
import com.abanoub.studynotes.screens.components.SubjectListBottomSheet
import com.abanoub.studynotes.screens.task.composables.TaskDatePicker
import com.abanoub.studynotes.util.Priority
import com.abanoub.studynotes.util.SnackBarEvent
import com.abanoub.studynotes.util.changeMillisToDateString
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    onBackButtonClicked: () -> Unit
){

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    val viewModel = hiltViewModel<TaskViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    var taskTitleError by rememberSaveable { mutableStateOf<String?>(null) }
    taskTitleError = when {
        state.title.isBlank() -> "Please enter task title."
        state.title.length < 4 -> "Task title is too short."
        state.title.length > 30 -> "Task title is too long."
        else -> null
    }

    var isDeleteTaskDialogOpen by rememberSaveable { mutableStateOf(false) }

    var isDatePickerDialogOpen by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectDate = Instant.ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                val currentDate = LocalDate.now(ZoneId.systemDefault())
                return selectDate >= currentDate
            }
        }
    )

    var isSubjectBottomSheetOpen by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    val snackBarHostState = remember { SnackbarHostState() }


    DeleteDialog(
        isOpen = isDeleteTaskDialogOpen,
        title = "Delete Task",
        description = "Are you sure, you want to delete this task?" +
                " This action can not be undo",
        onDismissRequest = { isDeleteTaskDialogOpen = false },
        onConfirmButtonClick = {
            viewModel.onEvent(TaskEvents.DeleteTask)
            isDeleteTaskDialogOpen = false
        }
    )

    TaskDatePicker(
        isOpen = isDatePickerDialogOpen,
        state = datePickerState,
        onDismissRequest = { isDatePickerDialogOpen = false },
        onConfirmButtonClicked = {
            viewModel.onEvent(TaskEvents.OnDateChange(datePickerState.selectedDateMillis))
            isDatePickerDialogOpen = false
        }
    )

    SubjectListBottomSheet(
        sheetState = bottomSheetState,
        isOpen = isSubjectBottomSheetOpen,
        subjects = state.subjects,
        onSubjectClicked = {
            viewModel.onEvent(TaskEvents.OnRelatedSubjectSelect(it))
            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) isSubjectBottomSheetOpen = false
            }
        },
        onDismissRequest = { isSubjectBottomSheetOpen = false }
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


    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TaskTopBar(
                isTaskExist = state.currentTaskId != null,
                isCompleted = state.isTaskComplete,
                checkboxBorderColor = state.priority.color,
                onBackButtonClick = onBackButtonClicked,
                onDeleteButtonClick = { isDeleteTaskDialogOpen = true },
                onCheckboxClick = { viewModel.onEvent(TaskEvents.OnIsCompleteChange) }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(it)
                .padding(horizontal = 12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.title,
                onValueChange = { viewModel.onEvent(TaskEvents.OnTitleChange(it)) },
                label = { Text(text = "Title") },
                singleLine = true,
                isError = taskTitleError != null && state.title.isNotBlank(),
                supportingText = { Text(text = taskTitleError.orEmpty()) }
            )

            Spacer(modifier = Modifier.height(10.dp))
            
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.description,
                onValueChange = { viewModel.onEvent(TaskEvents.OnDescriptionChange(it)) },
                label = { Text(text = "Description") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Due Date",
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = state.dueDate.changeMillisToDateString(),
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(
                    onClick = { isDatePickerDialogOpen = true }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_date_range),
                        contentDescription = "Select due date"
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Priority",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Priority.entries.forEach { priority ->
                    PriorityButton(
                        modifier = Modifier.weight(1f),
                        label = priority.title,
                        backgroundColor = priority.color,
                        borderColor = if (priority == state.priority){
                            Color.White
                        } else { Color.Transparent },
                        labelColor = if (priority == state.priority){
                            Color.White
                        } else { Color.White.copy(alpha = 0.7f) },
                        onClick = {
                            viewModel.onEvent(TaskEvents.OnPriorityChange(priority))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Related to subject",
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                val firstSubject = state.subjects.firstOrNull()?.name ?: ""
                Text(
                    text = state.relatedToSubject ?: firstSubject,
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(
                    onClick = { isSubjectBottomSheetOpen = true }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_drop_down),
                        contentDescription = "Select subject"
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 20.dp),
                enabled = taskTitleError == null,
                onClick = {
                    viewModel.onEvent(TaskEvents.SaveTask)
                }
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview(){
    TaskScreen(
        onBackButtonClicked = {}
    )
}