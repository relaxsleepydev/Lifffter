package com.example.lifffter.feature_tracking.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class WorkoutSessionDto(
    @SerializedName("id") val id: UUID,
    @SerializedName("routine_id") val routineId: UUID?,
    @SerializedName("start_time") val startTime: Long,
    @SerializedName("end_time") val endTime: Long?
)
