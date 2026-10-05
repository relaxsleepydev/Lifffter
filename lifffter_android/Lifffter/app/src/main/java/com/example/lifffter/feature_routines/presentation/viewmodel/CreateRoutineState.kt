package com.example.lifffter.feature_routines.presentation.viewmodel

import com.example.lifffter.feature_exercise.data.local.ExerciseEntity

data class CreateRoutineState(
    val routineName: String = "",
    val targetMuscle: String = "",
    val selectedExercises: List<ExerciseEntity> = emptyList()
)