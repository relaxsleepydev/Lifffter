package com.example.lifffter.core.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // harcoding token for now
        val token = " "

        // grabbing the request
        val request: Request = chain.request()

        // adding header
        val newRequest = request
            .newBuilder()
            .addHeader("Authorization", "Bearer ${token}")
            .build()

        // returning it
        return chain.proceed(newRequest)
    }
}