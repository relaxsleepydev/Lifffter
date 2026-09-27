package com.example.lifffter.feature_exercise.data.mapper

import com.example.lifffter.feature_exercise.data.local.ExerciseEntity
import com.example.lifffter.feature_exercise.data.remote.ExerciseDto
import com.example.lifffter.feature_exercise.domain.model.Exercise

fun ExerciseDto.toEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = this.id,
        name = this.name,
        primaryMuscle = this.primaryMuscle
    )
}

fun ExerciseEntity.toDomain(): Exercise {
    return Exercise(
        id = this.id,
        name = this.name,
        primaryMuscle = this.primaryMuscle
    )
}