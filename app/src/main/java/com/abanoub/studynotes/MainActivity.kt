package com.abanoub.studynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.abanoub.studynotes.navigation.NavHost
import com.abanoub.studynotes.screens.theme.JCStudyNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            JCStudyNotesTheme {
                NavHost()
            }
        }
    }
}