package com.example.lifffter.feature_tracking.domain

import java.util.UUID

data class ActiveExercise(
    val exerciseId: UUID,
    val name: String,
    val sets: List<WorkoutSet> = emptyList()
)
