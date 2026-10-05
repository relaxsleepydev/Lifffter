package com.example.lifffter.feature_tracking.domain.models

import java.util.UUID

data class WorkoutHistoryItem(
    val sessionId: UUID,
    val exerciseName: List<String>,
    val date: Long,
    val totalSets: Int,
    val duration: Long,
    val totalReps: Int,
    val totalVolume: Float,
    val workoutTitle: String
)
