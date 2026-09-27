package com.example.lifffter.feature_exercise.presentation.viewmodel

import com.example.lifffter.feature_exercise.domain.model.Exercise

data class ExerciseUiState(
    val isLoading: Boolean = false,
    val exercises: List<Exercise> = emptyList(),
    val error: String? = null
)