package com.example.lifffter.core.workmanager

import android.annotation.SuppressLint
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.lifffter.feature_tracking.domain.repository.ActiveWorkoutRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.UUID

@HiltWorker
class SyncWorkoutWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val activeWorkoutRepository: ActiveWorkoutRepository
): CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val sessionIdString = inputData.getString(KEY_SESSION_ID) ?: return Result.failure()
            val sessionId = UUID.fromString(sessionIdString)

            activeWorkoutRepository.finishAndSyncWorkout(sessionId)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val KEY_SESSION_ID = "key_session_id"

        fun scheduleWorkoutSync(context: Context, sessionId: UUID) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val inputData = workDataOf(KEY_SESSION_ID to sessionId.toString())

            val syncWorkRequest = OneTimeWorkRequestBuilder<SyncWorkoutWorker>()
                .setConstraints(constraints)
                .setInputData(inputData)
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                "sync_workout_${sessionId}",
                ExistingWorkPolicy.KEEP,
                syncWorkRequest
            )
        }
    }
}