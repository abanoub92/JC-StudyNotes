package com.abanoub.studynotes.domain.model

import androidx.compose.ui.graphics.Color
import com.abanoub.studynotes.screens.theme.gradient1
import com.abanoub.studynotes.screens.theme.gradient2
import com.abanoub.studynotes.screens.theme.gradient3
import com.abanoub.studynotes.screens.theme.gradient4
import com.abanoub.studynotes.screens.theme.gradient5

data class Subject(
    val id: Int,
    val name: String,
    val goalHours: Float,
    val colors: List<Color>
) {
    companion object {
        val subjectCardColors = listOf(
            gradient1, gradient2, gradient3, gradient4, gradient5
        )
    }
}
