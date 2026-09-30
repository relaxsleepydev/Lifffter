package com.example.lifffter.feature_tracking.domain.repository

import com.example.lifffter.feature_tracking.data.local.SetLogsEntity
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionEntity
import com.example.lifffter.feature_tracking.domain.models.WorkoutSession
import com.example.lifffter.feature_tracking.domain.models.WorkoutSet
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ActiveWorkoutRepository {
    fun getActiveSession(sessionId: UUID): Flow<WorkoutSession?>

    suspend fun insertSession(session: WorkoutSession)

    suspend fun insertSet(set: WorkoutSet, sessionId: UUID, exerciseId: UUID)

    suspend fun deleteSession(id: UUID)

    suspend fun deleteSet(id: UUID)

    suspend fun updateSetWeight(setId: UUID, weight: Float)

    suspend fun updateSetReps(setId: UUID, reps: Int)

    suspend fun toggleSetComplete(setId: UUID, isCompleted: Boolean)
}