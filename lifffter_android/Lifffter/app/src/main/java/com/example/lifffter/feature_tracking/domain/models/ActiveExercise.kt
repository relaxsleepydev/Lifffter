package com.example.lifffter.feature_tracking.domain.models

import java.util.UUID

data class ActiveExercise(
    val exerciseId: String,
    val name: String,
    val sets: List<WorkoutSet> = emptyList()
)
