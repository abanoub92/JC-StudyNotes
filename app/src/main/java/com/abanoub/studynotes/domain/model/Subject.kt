package com.abanoub.studynotes.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abanoub.studynotes.screens.theme.gradient1
import com.abanoub.studynotes.screens.theme.gradient2
import com.abanoub.studynotes.screens.theme.gradient3
import com.abanoub.studynotes.screens.theme.gradient4
import com.abanoub.studynotes.screens.theme.gradient5

@Entity
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val goalHours: Float,
    val colors: List<Int>
) {
    companion object {
        val subjectCardColors = listOf(
            gradient1, gradient2, gradient3, gradient4, gradient5
        )

        fun toColorIntList(colorList: List<Color>): List<Int> {
            return colorList.map { it.toArgb() }.toList()
        }

        fun toColorList(colorIntList: List<Int>): List<Color> {
            return colorIntList.map { Color(it) }.toList()
        }
    }
}
