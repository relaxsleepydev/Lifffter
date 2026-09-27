package com.example.lifffter.feature_tracking.domain

import java.util.UUID

data class WorkoutSession(
    val id: UUID,
    val routineId: UUID? = null,
    val startTime: Long,
    val endTime: Long? = null,
    val exercises: List<ActiveExercise> = emptyList()
)
