package com.example.lifffter.feature_exercise.data.remote

import com.google.gson.annotations.SerializedName

data class ExerciseDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("primary_muscle") val primaryMuscle: String
)
