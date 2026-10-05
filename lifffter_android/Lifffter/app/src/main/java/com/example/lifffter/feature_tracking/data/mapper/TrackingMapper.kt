package com.example.lifffter.feature_tracking.data.mapper

import com.example.lifffter.feature_tracking.data.local.SetLogsEntity
import com.example.lifffter.feature_tracking.data.local.SetWithExerciseFlat
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionEntity
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionWithSets
import com.example.lifffter.feature_tracking.domain.models.ActiveExercise
import com.example.lifffter.feature_tracking.domain.models.WorkoutHistoryItem
import com.example.lifffter.feature_tracking.domain.models.WorkoutSession
import com.example.lifffter.feature_tracking.domain.models.WorkoutSet
import kotlin.collections.map
import kotlin.math.exp

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

fun Map.Entry<WorkoutSessionEntity, List<SetWithExerciseFlat>>.toDomain(): WorkoutHistoryItem {
    val session = this.key
    val sets = this.value

    val durationMillis = if(session.endTime != null) {
        session.endTime - session.startTime
    } else {
        0L
    }


    val totalReps = sets.sumOf { it.reps }

    val totalVolume = sets.sumOf { (it.weight * it.reps).toDouble() }.toFloat()

    val uniqueExerciseNames = sets.map { it.exerciseName }.distinct()

    return WorkoutHistoryItem(
        sessionId = session.id,
        date = session.startTime,
        duration = durationMillis,
        totalSets = sets.size,
        exerciseName = uniqueExerciseNames,
        totalReps = totalReps,
        totalVolume = totalVolume,
        workoutTitle = uniqueExerciseNames.firstOrNull() ?: "Completed Workout"
    )
}