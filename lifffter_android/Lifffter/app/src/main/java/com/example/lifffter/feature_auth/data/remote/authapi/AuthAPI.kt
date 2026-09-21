package com.example.lifffter.feature_auth.data.remote.authapi

import com.example.lifffter.feature_auth.data.remote.authDto.LoginRequestDTO
import com.example.lifffter.feature_auth.data.remote.authDto.LoginResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("api/v1/auth/login")
    suspend fun loginUser(@Body request: LoginRequestDTO): LoginResponseDTO
    // body annotation tells retrofit to take the object, convert it to std JSON,
    // and stuff it into the HTTP POST request to make the server read it.
}