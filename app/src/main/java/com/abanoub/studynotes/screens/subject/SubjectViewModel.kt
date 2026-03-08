package com.abanoub.studynotes.screens.subject

import androidx.lifecycle.ViewModel
import com.abanoub.studynotes.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel
@Inject constructor(
    private val subjectRepository: SubjectRepository
): ViewModel() {}