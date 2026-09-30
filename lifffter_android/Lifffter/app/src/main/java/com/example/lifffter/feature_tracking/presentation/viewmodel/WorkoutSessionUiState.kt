package com.example.lifffter.feature_tracking.presentation.viewmodel

import com.example.lifffter.feature_tracking.domain.models.WorkoutSession

data class WorkoutSessionUiState(
    val loading: Boolean = false,
    val workoutSession: WorkoutSession? = null,
    val error: String? = null
)
