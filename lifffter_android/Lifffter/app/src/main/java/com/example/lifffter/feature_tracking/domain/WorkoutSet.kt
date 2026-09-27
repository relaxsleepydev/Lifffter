package com.example.lifffter.feature_tracking.domain

import java.util.UUID

data class WorkoutSet(
    val id: UUID,
    val weight: Float = 0f,
    val reps: Int = 0,
    val rir: Int = 0,
    val isCompleted: Boolean = false
)
