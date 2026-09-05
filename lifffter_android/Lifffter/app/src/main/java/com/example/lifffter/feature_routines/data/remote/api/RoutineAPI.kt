package com.example.lifffter.feature_routines.data.remote.api

import com.example.lifffter.feature_routines.data.remote.dto.RoutineDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RoutineAPI {
    @GET("api/v1/workouts")
    suspend fun fetchRoutines(): List<RoutineDTO>

    @POST("api/v1/workouts")
    suspend fun pushRoutine(@Body routine: RoutineDTO)
}