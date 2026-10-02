package com.example.lifffter.feature_tracking.presentation.viewmodel

import com.example.lifffter.feature_tracking.domain.models.WorkoutSession
import com.example.lifffter.feature_tracking.domain.models.WorkoutSet
import java.util.UUID

sealed class WorkoutSessionEvent {
    data object SyncSession: WorkoutSessionEvent()
    data class AddSet(val exerciseId: String): WorkoutSessionEvent()
    data class UpdateSetWeight(val setId: UUID, val weight: Float): WorkoutSessionEvent()
    data class UpdateSetReps(val setId: UUID, val reps: Int): WorkoutSessionEvent()
    data class ToggleSetComplete(val setId: UUID, val isCompleted: Boolean): WorkoutSessionEvent()
    data class AddExercise(val exerciseId: String): WorkoutSessionEvent()
    data object FinishWorkout: WorkoutSessionEvent()
}