package com.example.lifffter.feature_routines.data.repository

import android.util.Log
import com.example.lifffter.feature_routines.data.local.RoutineDAO
import com.example.lifffter.feature_routines.data.local.RoutineEntity
import com.example.lifffter.feature_routines.data.remote.api.RoutineApi
import com.example.lifffter.core.domain.model.Routine
import com.example.lifffter.feature_exercise.data.local.ExerciseEntity
import com.example.lifffter.feature_routines.data.local.RoutineExerciseCrossRef
import com.example.lifffter.feature_routines.domain.model.RoutineWithExercises
import com.example.lifffter.feature_routines.domain.repository.RoutineRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class RoutineRepositoryImpl @Inject constructor(
    private val dao: RoutineDAO,
    private val api: RoutineApi
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
            // fetching routines from server.
            val dtos = api.fetchRoutines()

            // mapping the dto to entities using .map
            val mappedEntities = dtos.map { routineDTO ->
                RoutineEntity(
                    id = routineDTO.id,
                    name = routineDTO.name,
                    targetMuscleGroup = routineDTO.targetMuscleGroup
                )
            }

            // inserted the mapped entities in the db
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

    override fun getRoutineWithExercises(): Flow<List<RoutineWithExercises>> {
        return dao.getRoutineWithExercises()
    }

    override suspend fun insertRoutineWithExercises(
        routine: RoutineEntity,
        exercises: List<ExerciseEntity>
    ) {
        dao.insertRoutinesWithExercises(routine, exercises.map { exercise ->
            RoutineExerciseCrossRef(routine.id, exercise.id)
        })
    }
}