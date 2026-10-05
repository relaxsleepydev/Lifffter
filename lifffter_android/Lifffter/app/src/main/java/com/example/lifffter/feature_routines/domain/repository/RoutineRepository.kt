package com.example.lifffter.feature_routines.domain.repository

import com.example.lifffter.core.domain.model.Routine
import com.example.lifffter.feature_exercise.data.local.ExerciseEntity
import com.example.lifffter.feature_routines.data.local.RoutineEntity
import com.example.lifffter.feature_routines.data.local.RoutineExerciseCrossRef
import com.example.lifffter.feature_routines.domain.model.RoutineWithExercises
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getRoutines(): Flow<List<Routine>>

    suspend fun syncRoutine()

    suspend fun insertRoutine(routine: Routine)

    fun getRoutineWithExercises(): Flow<List<RoutineWithExercises>>

    suspend fun insertRoutineWithExercises(routine: RoutineEntity, exercises: List<ExerciseEntity>)
}