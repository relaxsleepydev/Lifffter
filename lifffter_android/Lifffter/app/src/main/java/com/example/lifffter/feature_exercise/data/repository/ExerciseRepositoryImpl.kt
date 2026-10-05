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
            val dtoresponse = api.fetchExercises()
//            Log.d("NetworkDebug", "A - Retrofit parsed response: success=({response.success}, items={response.exercises?.size}")
            val remoteExercises = dtoresponse.exercises
//            Log.d("NetworkDebug", "Fetched ${remoteExercises.size} exercises from the API")
            val result = remoteExercises.map {
                it.toEntity()
            }
//            Log.d("NetworkDebug", "B - Mapped ${remoteExercises.size} entities ready for Room")
            dao.replaceExercises(result)
//            Log.d("NetworkDebug", "C - Insertion command completed without crashing")
        } catch(e: Exception) {
            e.printStackTrace()
//            Log.e("NetworkDebug", "Sync crashed: ${e.message}", e)
        }
    }

    override fun getExerciseById(id: String): ExerciseEntity? {
        return dao.getExerciseById(id)
    }
}