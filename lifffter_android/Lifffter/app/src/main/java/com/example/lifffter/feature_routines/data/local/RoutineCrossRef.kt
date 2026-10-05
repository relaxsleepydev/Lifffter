package com.example.lifffter.feature_routines.data.local

import androidx.room.Entity

@Entity(primaryKeys = ["routineId", "exerciseId"])
data class RoutineExerciseCrossRef(
    val routineId: String,
    val exerciseId: String
)
