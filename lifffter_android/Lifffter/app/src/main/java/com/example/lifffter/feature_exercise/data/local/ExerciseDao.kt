package com.example.lifffter.feature_exercise.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise_catalog")
    fun getExercises(): Flow<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)

    @Query("DELETE FROM exercise_catalog")
    suspend fun deleteAllExercises()

    @Transaction
    suspend fun replaceExercises(exercises: List<ExerciseEntity>) {
        deleteAllExercises()
        insertExercises(exercises)
    }
}