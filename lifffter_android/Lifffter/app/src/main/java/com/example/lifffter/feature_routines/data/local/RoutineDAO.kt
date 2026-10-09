package com.example.lifffter.feature_routines.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.lifffter.feature_routines.domain.model.RoutineWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRoutine(routine: RoutineEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRoutines(routine: List<RoutineEntity>)

    @Query("SELECT * FROM routine_table")
    fun getAllRoutines(): Flow<List<RoutineEntity>>


    @Transaction
    @Query("SELECT * FROM routine_table")
    fun getRoutineWithExercises(): Flow<List<RoutineWithExercises>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRoutineCrossRef(routineCrossRef: List<RoutineExerciseCrossRef>)

    @Transaction
    suspend fun insertRoutinesWithExercises(routine: RoutineEntity, routineCrossRef: List<RoutineExerciseCrossRef>) {
        insertRoutine(routine)
        insertRoutineCrossRef(routineCrossRef)
    }

    @Transaction
    @Query("SELECT * FROM routine_table WHERE id = :routineId")
    suspend fun getRoutineWithExerciseById(routineId: String): RoutineWithExercises?
}