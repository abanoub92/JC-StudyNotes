package com.abanoub.studynotes.screens.landing.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingTopBar(){
    TopAppBar(
        title = {
            Text(
                "Study Notes",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )
}