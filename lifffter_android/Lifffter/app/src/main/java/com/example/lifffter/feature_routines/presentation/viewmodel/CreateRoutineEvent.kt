package com.example.lifffter.feature_routines.presentation.viewmodel

sealed class CreateRoutineEvent {
    data class OnNameChange(val name: String): CreateRoutineEvent()
    data class OnTargetMuscleChange(val targetMuscleGroup: String): CreateRoutineEvent()
    data class OnAddExercise(val exerciseId: String): CreateRoutineEvent()
    data class OnRemoveExercise(val exerciseId: String): CreateRoutineEvent()
    data object OnSaveRoutine: CreateRoutineEvent()
}