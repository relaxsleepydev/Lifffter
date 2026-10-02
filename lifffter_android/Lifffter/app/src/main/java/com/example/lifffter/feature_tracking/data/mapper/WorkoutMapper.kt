package com.example.lifffter.feature_tracking.data.mapper

import com.example.lifffter.feature_tracking.data.remote.dto.WorkoutSessionDto
import com.example.lifffter.feature_tracking.data.remote.dto.WorkoutSetDto
import com.example.lifffter.feature_tracking.domain.models.WorkoutSession

fun WorkoutSession.toDto(): WorkoutSessionDto {
    return WorkoutSessionDto(
        routineId = routineId!!
    )
}

fun WorkoutSession.toSetDtoList(): List<WorkoutSetDto> {
    val mutableList = mutableListOf<WorkoutSetDto>()
    this.exercises.forEach { exercise ->
        exercise.sets.forEachIndexed { index, set ->
            mutableList.add(
                WorkoutSetDto(
                    sessionId = this.id,
                    exerciseId = exercise.exerciseId,
                    setNumber = index + 1,
                    weight = set.weight,
                    reps = set.reps,
                    rir = set.rir
                )
            )
        }
    }
    return mutableList
}