package com.example.lifffter.feature_tracking.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.lifffter.feature_exercise.data.local.ExerciseEntity
import com.example.lifffter.feature_tracking.domain.WorkoutSession
import java.util.UUID

@Entity(
    tableName = "set_logs",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSessionEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("sessionId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("exerciseId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["sessionId"]),
        Index(value = ["exerciseId"])
    ]
)
data class SetLogsEntity(
    @PrimaryKey
    val id: UUID,
    val weight: Float = 0f,
    val reps: Int = 0,
    val rir: Int = 0,
    val isCompleted: Boolean = false,
    val isDeleted: Boolean = false ,
    val sessionId: UUID,
    val exerciseId: UUID,
    val createdAt: Long
)