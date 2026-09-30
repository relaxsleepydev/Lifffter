package com.example.lifffter.feature_tracking.data.mapper

import com.example.lifffter.feature_tracking.data.local.SetLogsEntity
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionWithSets
import com.example.lifffter.feature_tracking.domain.models.ActiveExercise
import com.example.lifffter.feature_tracking.domain.models.WorkoutSession
import com.example.lifffter.feature_tracking.domain.models.WorkoutSet
import kotlin.collections.map

fun WorkoutSessionWithSets.toDomain(): WorkoutSession {
    return WorkoutSession(
        id = this.workoutSession.id,
        routineId = this.workoutSession.routineId,
        startTime = this.workoutSession.startTime,
        endTime = this.workoutSession.endTime,
        exercises = this.sets.groupBy { it.exerciseId }.map { (exerciseId, setsList) ->
            ActiveExercise(
                exerciseId = exerciseId,
                name = " ",
                sets = setsList.map { it.toDomain() }
            )
        }
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