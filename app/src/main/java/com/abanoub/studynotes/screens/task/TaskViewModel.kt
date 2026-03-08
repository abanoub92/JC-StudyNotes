package com.abanoub.studynotes.screens.task

import androidx.lifecycle.ViewModel
import com.abanoub.studynotes.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel
@Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {}