package com.abanoub.studynotes.screens.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DeleteDialog(
    isOpen: Boolean,
    title: String,
    description: String,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
){

    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = title) },
            text = { Text(text = description) },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteDialogPreview(){
    DeleteDialog(
        isOpen = true,
        title = "",
        description = "",
        onDismissRequest = {},
        onConfirmButtonClick = {}
    )
}