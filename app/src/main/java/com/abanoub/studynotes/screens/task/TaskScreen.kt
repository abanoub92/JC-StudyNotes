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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abanoub.studynotes.screens.task.composables.TaskTopBar
import com.abanoub.studynotes.R
import com.abanoub.studynotes.screens.components.DeleteDialog
import com.abanoub.studynotes.screens.task.composables.PriorityButton
import com.abanoub.studynotes.util.Priority

@Composable
fun TaskScreen(){

    val scrollState = rememberScrollState()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var isDeleteTaskDialogOpen by rememberSaveable { mutableStateOf(false) }

    var taskTitleError by rememberSaveable { mutableStateOf<String?>(null) }
    taskTitleError = when {
        title.isBlank() -> "Please enter task title."
        title.length < 4 -> "Task title is too short."
        title.length > 30 -> "Task title is too long."
        else -> null
    }

    DeleteDialog(
        isOpen = isDeleteTaskDialogOpen,
        title = "Delete Task",
        description = "Are you sure, you want to delete this task?" +
                " This action can not be undo",
        onDismissRequest = { isDeleteTaskDialogOpen = false },
        onConfirmButtonClick = { isDeleteTaskDialogOpen = false }
    )

    Scaffold(
        topBar = {
            TaskTopBar(
                isTaskExist = true,
                isCompleted = false,
                checkboxBorderColor = Color.Red,
                onBackButtonClick = {},
                onDeleteButtonClick = { isDeleteTaskDialogOpen = true },
                onCheckboxClick = {}
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
                value = title,
                onValueChange = { title = it },
                label = { Text(text = "Title") },
                singleLine = true,
                isError = taskTitleError != null && title.isNotBlank(),
                supportingText = { Text(text = taskTitleError.orEmpty()) }
            )

            Spacer(modifier = Modifier.height(10.dp))
            
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = { description = it },
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
                    text = "5 March, 2026",
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(
                    onClick = {}
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
                        borderColor = if (priority == Priority.MEDIUM){
                            Color.White
                        } else { Color.Transparent },
                        labelColor = if (priority == Priority.MEDIUM){
                            Color.White
                        } else { Color.White.copy(alpha = 0.7f) },
                        onClick = {}
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

                Text(
                    text = "Test data",
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(
                    onClick = {}
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
                onClick = {}
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview(){
    TaskScreen()
}