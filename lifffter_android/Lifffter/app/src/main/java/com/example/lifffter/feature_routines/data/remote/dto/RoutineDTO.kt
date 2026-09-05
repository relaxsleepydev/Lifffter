package com.example.lifffter.feature_routines.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RoutineDTO (
    @SerializedName("id") val id: String,
    @SerializedName("routine_name") val name: String,
    @SerializedName("target_muscle") val targetMuscleGroup: String
)