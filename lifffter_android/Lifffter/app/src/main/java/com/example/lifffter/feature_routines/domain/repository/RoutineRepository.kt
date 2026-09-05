package com.example.lifffter.feature_routines.domain.repository

import com.example.lifffter.feature_routines.data.local.RoutineEntity
import com.example.lifffter.feature_routines.domain.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getRoutines(): Flow<List<Routine>>

    suspend fun syncRoutine()

    suspend fun insertRoutine(routine: Routine)
}