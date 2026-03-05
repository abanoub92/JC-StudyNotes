package com.abanoub.studynotes.screens.task.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.abanoub.studynotes.R
import com.abanoub.studynotes.screens.components.TaskCheckbox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar(
    isTaskExist: Boolean,
    isCompleted: Boolean,
    checkboxBorderColor: Color,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onCheckboxClick: () -> Unit,
){
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "Navigate back"
                )
            }
        },
        title = {
            Text(
                text = "Task",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            if (isTaskExist){
                TaskCheckbox(
                    isCompleted = isCompleted,
                    borderColor = checkboxBorderColor,
                    onCheckboxClicked = onCheckboxClick
                )

                IconButton(
                    onClick = onDeleteButtonClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = "Delete task"
                    )
                }
            }
        }
    )
}