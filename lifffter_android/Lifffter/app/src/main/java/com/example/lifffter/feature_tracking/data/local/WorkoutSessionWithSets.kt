package com.example.lifffter.feature_tracking.data.local

import androidx.room.Embedded
import androidx.room.Relation
import java.util.UUID

data class WorkoutSessionWithSets(
    @Embedded val workoutSession: WorkoutSessionEntity,
    @Relation(
        parentColumn = "id", // id in workoutSessionEntity
        entityColumn = "sessionId" // sessionId in SetLogsEntity
    ) val sets: List<SetLogsEntity>
)

data class SetWithExerciseFlat(
    val setId: UUID,
    val weight: Float,
    val reps: Int,
    val rir: Int,
    val isCompleted: Boolean,
    val sessionId: UUID,
    val exerciseId: String,
    val exerciseName: String // Pulled from the catalog table
)