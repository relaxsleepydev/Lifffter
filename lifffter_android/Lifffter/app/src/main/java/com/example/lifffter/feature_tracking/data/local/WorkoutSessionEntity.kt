package com.example.lifffter.feature_tracking.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "workout_session")
data class WorkoutSessionEntity(
    @PrimaryKey
    val id: UUID,
    val routineId: UUID? = null,
    val startTime: Long,
    val endTime: Long? = null,
    val isDeleted: Boolean = false
)
