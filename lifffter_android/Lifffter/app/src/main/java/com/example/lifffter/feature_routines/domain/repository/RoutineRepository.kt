package com.example.lifffter.feature_routines.domain.repository

import com.example.lifffter.core.domain.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getRoutines(): Flow<List<Routine>>

    suspend fun syncRoutine()

    suspend fun insertRoutine(routine: Routine)
}