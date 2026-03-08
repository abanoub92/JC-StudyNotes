package com.abanoub.studynotes.screens.landing

import androidx.lifecycle.ViewModel
import com.abanoub.studynotes.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel
@Inject constructor(
    private val subjectRepository: SubjectRepository
): ViewModel() {}