package com.example.lifffter.feature_routines.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "routine_table")
data class RoutineEntity (
    @PrimaryKey
    val id: String, // will look at it in future if needs change
    val name: String,
    val targetMuscleGroup: String
)