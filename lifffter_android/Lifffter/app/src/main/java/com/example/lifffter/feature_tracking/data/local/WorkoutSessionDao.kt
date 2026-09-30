package com.example.lifffter.feature_tracking.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface WorkoutSessionDao {

    @Query("SELECT * FROM workout_session WHERE id = :id")
    fun getWorkoutSession(id: UUID): Flow<WorkoutSessionWithSets?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: WorkoutSessionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: SetLogsEntity)

    @Query("UPDATE workout_session SET isDeleted = 1 WHERE id = :id")
    suspend fun deleteSession(id: UUID)

    @Query("UPDATE set_logs SET isDeleted = 1 WHERE id = :id")
    suspend fun deleteSet(id: UUID)

    @Query("UPDATE set_logs SET weight = :weight WHERE id = :id")
    suspend fun updateSetWeight(id: UUID, weight: Float)

    @Query("UPDATE set_logs SET reps = :reps WHERE id = :id")
    suspend fun updateSetReps(id: UUID, reps: Int)

    @Query("UPDATE set_logs SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun toggleSetComplete(id: UUID, isCompleted: Boolean)
}
