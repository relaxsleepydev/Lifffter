package com.example.lifffter.feature_tracking.data.mapper

import com.example.lifffter.feature_tracking.data.local.SetLogsEntity
import com.example.lifffter.feature_tracking.data.local.SetWithExerciseFlat
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionEntity
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionWithSets
import com.example.lifffter.feature_tracking.domain.models.ActiveExercise
import com.example.lifffter.feature_tracking.domain.models.WorkoutSession
import com.example.lifffter.feature_tracking.domain.models.WorkoutSet
import kotlin.collections.map

fun List<SetWithExerciseFlat>.toDomainWorkout(sessionEntity: WorkoutSessionEntity): WorkoutSession {
    // Group all rows by exerciseId so identical exercises bundle together
    val exercisesGrouped = this.groupBy { it.exerciseId }

    val activeExercises = exercisesGrouped.map { (exerciseId, rows) ->
        val firstRow = rows.first()
        ActiveExercise(
            exerciseId = exerciseId,
            name = firstRow.exerciseName ?: "Unknown Exercise", // Real name from SQL join!
            sets = rows.map { row ->
                WorkoutSet(
                    id = row.setId,
                    weight = row.weight,
                    reps = row.reps,
                    rir = row.rir,
                    isCompleted = row.isCompleted
                )
            }
        )
    }

    return WorkoutSession(
        id = sessionEntity.id,
        routineId = sessionEntity.routineId,
        startTime = sessionEntity.startTime,
        endTime = sessionEntity.endTime,
        exercises = activeExercises
    )
}

fun SetLogsEntity.toDomain(): WorkoutSet {
    return WorkoutSet(
        id = this.id,
        reps = this.reps,
        weight = this.weight,
        rir = this.rir,
        isCompleted = this.isCompleted
    )
}