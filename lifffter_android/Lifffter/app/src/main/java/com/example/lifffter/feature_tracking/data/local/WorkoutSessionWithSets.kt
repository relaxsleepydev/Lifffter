package com.example.lifffter.feature_tracking.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutSessionWithSets(
    @Embedded val workoutSession: WorkoutSessionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId"
    ) val sets: List<SetLogsEntity>
)
