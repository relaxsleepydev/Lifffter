package com.example.lifffter.feature_exercise.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_catalog")
data class ExerciseEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val primaryMuscle: String
)