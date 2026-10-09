package com.example.lifffter.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lifffter.feature_exercise.data.local.ExerciseDao
import com.example.lifffter.feature_exercise.data.local.ExerciseEntity
import com.example.lifffter.feature_routines.data.local.RoutineDAO
import com.example.lifffter.feature_routines.data.local.RoutineEntity
import com.example.lifffter.feature_routines.data.local.RoutineExerciseCrossRef
import com.example.lifffter.feature_tracking.data.local.SetLogsEntity
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionDao
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionEntity
import com.example.lifffter.feature_tracking.data.local.WorkoutSessionWithSets

@Database(
    entities = [
        RoutineEntity::class,
        ExerciseEntity::class,
        WorkoutSessionEntity::class,
        SetLogsEntity::class,
        RoutineExerciseCrossRef::class
    ],
    version = 3
)
abstract class LifffterDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDAO
    abstract fun exerciseDao(): ExerciseDao

    abstract fun activeWorkoutDao(): WorkoutSessionDao
}