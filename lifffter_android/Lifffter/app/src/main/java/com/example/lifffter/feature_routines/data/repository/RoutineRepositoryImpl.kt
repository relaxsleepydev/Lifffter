package com.example.lifffter.feature_routines.data.repository

import android.util.Log
import com.example.lifffter.feature_routines.data.local.RoutineDAO
import com.example.lifffter.feature_routines.data.local.RoutineEntity
import com.example.lifffter.feature_routines.data.remote.api.RoutineAPI
import com.example.lifffter.feature_routines.domain.model.Routine
import com.example.lifffter.feature_routines.domain.repository.RoutineRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class RoutineRepositoryImpl @Inject constructor(
    private val dao: RoutineDAO,
    private val api: RoutineAPI
): RoutineRepository {
    override fun getRoutines(): Flow<List<Routine>> {
        // Flow.map
        return dao.getAllRoutines().map { routineEntities ->
            // List.map
            routineEntities.map { routineEntity ->
                Routine(
                    id = UUID.fromString(routineEntity.id),
                    name = routineEntity.name,
                    targetMuscleGroup = routineEntity.targetMuscleGroup
                )
            }
        }
    }

    override suspend fun syncRoutine() {
        try {
            val dtos = api.fetchRoutines()
            val mappedEntities = dtos.map { routineDTO ->
                RoutineEntity(
                    id = routineDTO.id,
                    name = routineDTO.name,
                    targetMuscleGroup = routineDTO.targetMuscleGroup
                )
            }
            dao.insertRoutines(mappedEntities)
        } catch(error: Exception) {
            Log.e("Lifffter_Network", "Sync Failed", error)
            error.printStackTrace()
        }
    }

    override suspend fun insertRoutine(routine: Routine) {
        dao.insertRoutine(RoutineEntity(
            routine.id.toString(),
            routine.name,
            routine.targetMuscleGroup
        ))
    }
}