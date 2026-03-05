package com.abanoub.studynotes.screens.subject.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.abanoub.studynotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectTopBar(
    title: String,
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
){
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                onClick = onBackClicked
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "Navigate back"
                )
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(
                onClick = onEditClicked
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "Edit subject"
                )
            }

            IconButton(
                onClick = onDeleteClicked
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "Delete subject"
                )
            }
        }
    )
}