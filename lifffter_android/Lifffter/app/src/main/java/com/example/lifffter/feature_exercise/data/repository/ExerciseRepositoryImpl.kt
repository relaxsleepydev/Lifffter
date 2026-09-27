package com.example.lifffter.feature_exercise.data.repository

import android.util.Log
import com.example.lifffter.core.database.LifffterDatabase
import com.example.lifffter.core.database.LifffterDatabase_Impl
import com.example.lifffter.feature_exercise.data.local.ExerciseDao
import com.example.lifffter.feature_exercise.data.local.ExerciseEntity
import com.example.lifffter.feature_exercise.data.mapper.toDomain
import com.example.lifffter.feature_exercise.data.mapper.toEntity
import com.example.lifffter.feature_exercise.data.remote.ExerciseApi
import com.example.lifffter.feature_exercise.data.remote.ExerciseDto
import com.example.lifffter.feature_exercise.domain.model.Exercise
import com.example.lifffter.feature_exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val dao: ExerciseDao,
    private val api: ExerciseApi
): ExerciseRepository {

    override fun getExercises(): Flow<List<Exercise>> {
        return dao.getExercises().map { exercisesEntities ->
            exercisesEntities.map {
                it.toDomain()
            }
        }
    }

    override suspend fun syncExercises() {
        try {
            val dtoresponse = api.fetchExercises().exercises
            val result = dtoresponse.map {
                it.toEntity()
            }
            dao.replaceExercises(result)
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}