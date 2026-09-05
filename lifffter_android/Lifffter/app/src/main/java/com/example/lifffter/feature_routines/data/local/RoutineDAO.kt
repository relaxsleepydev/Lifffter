package com.example.lifffter.feature_routines.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: RoutineEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutines(routine: List<RoutineEntity>)

    @Query("SELECT * FROM routine_table")
    fun getAllRoutines(): Flow<List<RoutineEntity>>
}