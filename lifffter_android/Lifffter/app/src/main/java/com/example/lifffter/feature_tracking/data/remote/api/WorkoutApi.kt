package com.example.lifffter.feature_tracking.data.remote.api

import com.example.lifffter.feature_tracking.data.remote.dto.WorkoutSessionDto
import com.example.lifffter.feature_tracking.data.remote.dto.WorkoutSetDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WorkoutApi {
    @POST("api/v1/sessions")
    suspend fun pushSession(@Body session: WorkoutSessionDto): Response<WorkoutApiResponse<List<WorkoutSessionDto>>>

    @POST("api/v1/sets")
    suspend fun pushSet(@Body set: List<WorkoutSetDto>): Response<WorkoutApiResponse<List<WorkoutSetDto>>>
}