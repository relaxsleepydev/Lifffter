package com.example.lifffter.feature_routines.data.remote.response

import com.example.lifffter.feature_exercise.data.remote.ExerciseDto
import com.google.gson.annotations.SerializedName

data class ExerciseCatalogResponseDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val exercises: List<ExerciseDto>
)
