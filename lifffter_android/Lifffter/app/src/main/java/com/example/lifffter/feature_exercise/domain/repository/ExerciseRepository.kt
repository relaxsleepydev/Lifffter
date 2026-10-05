package com.example.lifffter.feature_exercise.domain.repository

import com.example.lifffter.feature_exercise.data.local.ExerciseEntity
import com.example.lifffter.feature_exercise.data.remote.ExerciseDto
import com.example.lifffter.feature_exercise.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    fun getExercises(): Flow<List<Exercise>>

    fun getExerciseById(id: String): ExerciseEntity?

    suspend fun syncExercises()
}