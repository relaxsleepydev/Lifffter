package com.example.lifffter.feature_routines.presentation

import com.example.lifffter.feature_routines.domain.model.Routine

data class RoutineUiState(
    val isLoading: Boolean = false,
    val routines: List<Routine> = emptyList(),
    val error: String? = null
)