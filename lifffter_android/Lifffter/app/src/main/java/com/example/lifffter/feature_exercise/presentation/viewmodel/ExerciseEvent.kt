package com.example.lifffter.feature_exercise.presentation.viewmodel

import com.example.lifffter.feature_exercise.domain.model.Exercise

sealed class ExerciseEvent {
    data object Refresh: ExerciseEvent()
    data class OnExerciseClick(val exercise: Exercise): ExerciseEvent()
    data object SyncExercise: ExerciseEvent()
}