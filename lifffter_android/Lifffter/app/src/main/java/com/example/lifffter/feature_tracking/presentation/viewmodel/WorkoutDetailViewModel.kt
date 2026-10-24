package com.example.lifffter.feature_tracking.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.lifffter.core.navigation.Screen
import com.example.lifffter.feature_tracking.domain.models.WorkoutSession
import com.example.lifffter.feature_tracking.domain.repository.ActiveWorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    private val repository: ActiveWorkoutRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val route = savedStateHandle.toRoute<Screen.WorkoutDetailScreen>()
    private val sessionId = UUID.fromString(route.sessionId)

    val workoutSession: StateFlow<WorkoutSession?> = repository
        .getActiveSession(sessionId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}