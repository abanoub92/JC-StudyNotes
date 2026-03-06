package com.abanoub.studynotes.screens.session.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.abanoub.studynotes.R

fun LazyListScope.sessionRelatedToSubject(
    modifier: Modifier,
    relatedToSubject: String,
    onSelectSubjectButtonClick: () -> Unit
){

    item {
        Column(
            modifier = modifier
        ) {
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
                    text = relatedToSubject,
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(
                    onClick = onSelectSubjectButtonClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_drop_down),
                        contentDescription = "Select subject"
                    )
                }
            }
        }
    }

}