package com.example.lifffter.feature_tracking.data.repository

import android.util.Log
import com.example.lifffter.feature_tracking.data.local.SetLogsEntity
import com.example.lifffter.feature_tracking.data.local.SetWithExerciseFlat
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionDao
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionEntity
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionWithSets
import com.example.lifffter.feature_tracking.data.mapper.toDomain
import com.example.lifffter.feature_tracking.data.mapper.toDomainWorkout
import com.example.lifffter.feature_tracking.data.mapper.toDto
import com.example.lifffter.feature_tracking.data.mapper.toSetDtoList
import com.example.lifffter.feature_tracking.data.remote.api.WorkoutApi
import com.example.lifffter.feature_tracking.data.remote.api.WorkoutApiResponse
import com.example.lifffter.feature_tracking.domain.models.WorkoutSession
import com.example.lifffter.feature_tracking.domain.models.WorkoutSet
import com.example.lifffter.feature_tracking.domain.repository.ActiveWorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class ActiveWorkoutRepositoryImpl @Inject constructor(
    private val dao: WorkoutSessionDao,
    private val api: WorkoutApi
) : ActiveWorkoutRepository {
    override fun getActiveSession(sessionId: UUID): Flow<WorkoutSession?> {
        // Getting the session entity flow
        val sessionFlow = dao.getSessionEntityFlow(sessionId)

        // Getting the flat sets with exercise names flow
        val setsFlow = dao.getSetsWithExerciseForSession(sessionId)

        // Combine both streams into one emission
        return combine(sessionFlow, setsFlow) { sessionEntity, flatSets ->
            if (sessionEntity == null) {
                null
            } else {
                // Use our new flat mapper function here!
                flatSets.toDomainWorkout(sessionEntity)
            }
        }
    }

    override suspend fun insertSession(session: WorkoutSession) {
        val entity = WorkoutSessionEntity(
            id = session.id,
            routineId = session.routineId,
            startTime = session.startTime,
            endTime = session.endTime,
            isDeleted = false
        )
        dao.insertSession(entity)
    }

    override suspend fun insertSet(set: WorkoutSet, sessionId: UUID, exerciseId: String) {
        val entity = SetLogsEntity(
            id = set.id,
            weight = set.weight,
            reps = set.reps,
            rir = set.rir,
            isCompleted = set.isCompleted,
            sessionId = sessionId,
            exerciseId = exerciseId,
            createdAt = System.currentTimeMillis()
        )
        dao.insertSet(entity)
    }

    val id = UUID.randomUUID()

    override suspend fun addExerciseToSession(sessionId: UUID, exerciseId: String) {
        try {
            val blankSet = SetLogsEntity(
                id = UUID.randomUUID(),
                weight = 0f,
                reps = 0,
                rir = 0,
                isCompleted = false,
                sessionId = sessionId,
                exerciseId = exerciseId,
                createdAt = System.currentTimeMillis()
            )
            Log.d("RoomDebug", "Trying to insert set for Session: sessionId and Exercise: exerciseId")
            dao.insertSet(blankSet)
            Log.d("RoomDebug", "Set inserted successfully!")
        } catch (e: Exception) {
            Log.e("RoomDebug", "SET FAILED: ${e.message}")
        }
    }

    override suspend fun finishAndSyncWorkout(sessionId: UUID) {
        try {
            dao.updateSessionEndTime(sessionId, System.currentTimeMillis())
            val sessionEntity = dao.getSessionEntity(sessionId) ?: return
            val flatSets = dao.getSetsWithExerciseForSession(sessionId).first()

            val domainSession = flatSets.toDomainWorkout(sessionEntity)

            val workoutDto = domainSession.toDto()
            val setDto = domainSession.toSetDtoList()

            val sessionResponse = api.pushSession(workoutDto)
            val setResponse = api.pushSet(setDto)

            if (sessionResponse.isSuccessful && setResponse.isSuccessful) {
                // Optional: If you want to mark them completed or deleted locally upon success
                // dao.deleteSession(sessionId)
            } else {
                Log.e("Sync Error", "Server Rejected: ${sessionResponse.code()}")
            }
        } catch (e: Exception) {
            Log.e("Sync Error", "Network Failed", e)
        }
    }

    override fun getWorkoutHistory(): Flow<Map<WorkoutSessionEntity, List<SetWithExerciseFlat>>> {
        return dao.getWorkoutHistory()
    }

    override suspend fun deleteSession(id: UUID) {
        return dao.deleteSession(id)
    }

    override suspend fun deleteSet(id: UUID) {
        return dao.deleteSet(id)
    }

    override suspend fun toggleSetComplete(setId: UUID, isCompleted: Boolean) {
        dao.toggleSetComplete(setId, isCompleted)
    }

    override suspend fun updateSetWeight(setId: UUID, weight: Float) {
        dao.updateSetWeight(setId, weight)
    }

    override suspend fun updateSetReps(setId: UUID, reps: Int) {
        dao.updateSetReps(setId, reps)
    }
}