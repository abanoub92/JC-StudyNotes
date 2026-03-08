package com.abanoub.studynotes.screens.session

import androidx.lifecycle.ViewModel
import com.abanoub.studynotes.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel
@Inject constructor(
    private val sessionRepository: SessionRepository
): ViewModel() {}