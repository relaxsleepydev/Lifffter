package com.example.lifffter.feature_tracking.data.repository

import com.example.lifffter.feature_tracking.data.local.SetLogsEntity
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionDao
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionEntity
import com.example.lifffter.feature_tracking.data.mapper.toDomain
import com.example.lifffter.feature_tracking.domain.models.WorkoutSession
import com.example.lifffter.feature_tracking.domain.models.WorkoutSet
import com.example.lifffter.feature_tracking.domain.repository.ActiveWorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class ActiveWorkoutRepositoryImpl @Inject constructor(
    private val dao: WorkoutSessionDao
) : ActiveWorkoutRepository {
    override fun getActiveSession(sessionId: UUID): Flow<WorkoutSession?> {
        return dao.getWorkoutSession(sessionId).map {
            it?.toDomain()
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

    override suspend fun insertSet(set: WorkoutSet, sessionId: UUID, exerciseId: UUID) {
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