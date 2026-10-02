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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSession(session: WorkoutSessionEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSessionDummy(session: WorkoutSessionEntity)

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

    @Query("""
    SELECT s.id as setId, s.weight, s.reps, s.rir, s.isCompleted, s.sessionId, s.exerciseId,
           e.name as exerciseName 
    FROM set_logs s
    LEFT JOIN exercise_catalog e ON s.exerciseId = e.id
    WHERE s.sessionId = :sessionId AND s.isDeleted = 0
""")
    fun getSetsWithExerciseForSession(sessionId: UUID): Flow<List<SetWithExerciseFlat>>

    @Query("SELECT * FROM workout_session WHERE id = :sessionId")
    suspend fun getSessionEntity(sessionId: UUID): WorkoutSessionEntity?
    // Or as a Flow if the session itself changes, but a simple suspend or Flow works:
    @Query("SELECT * FROM workout_session WHERE id = :sessionId")
    fun getSessionEntityFlow(sessionId: UUID): Flow<WorkoutSessionEntity?>
}
