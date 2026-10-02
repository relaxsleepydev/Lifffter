package com.example.lifffter.feature_tracking.data.remote.api

data class WorkoutApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T
)