package com.example.lifffter.feature_exercise.data.remote

import com.example.lifffter.feature_routines.data.remote.response.ExerciseCatalogResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface ExerciseApi {
    @GET("api/v1/exercises")
    suspend fun fetchExercises(): ExerciseCatalogResponseDto
}