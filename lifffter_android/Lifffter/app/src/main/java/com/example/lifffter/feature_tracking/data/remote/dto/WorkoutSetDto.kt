package com.example.lifffter.feature_tracking.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class WorkoutSetDto(
    @SerializedName("session_id") val sessionId: UUID,
    @SerializedName("exercise_id") val exerciseId: String,
    @SerializedName("set_number") val setNumber: Int,
    @SerializedName("weight") val weight: Float,
    @SerializedName("reps") val reps: Int,
    @SerializedName("rir") val rir: Int
)