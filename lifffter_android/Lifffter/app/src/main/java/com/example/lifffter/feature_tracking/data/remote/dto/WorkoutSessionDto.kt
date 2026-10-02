package com.example.lifffter.feature_tracking.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class WorkoutSessionDto(
    @SerializedName("routine_id") val routineId: UUID
)
