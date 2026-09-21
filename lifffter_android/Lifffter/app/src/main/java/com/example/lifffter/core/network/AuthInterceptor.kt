package com.example.lifffter.core.network

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.work.Data
import com.example.lifffter.core.security.DataStorePreferences
import jakarta.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val dataStorePreferences: DataStorePreferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStorePreferences.getSecurePreference(
                stringPreferencesKey("token"),
                ""
            ).firstOrNull()
        }

        // grabbing the request
        val request: Request = chain.request()

        val checking = request.url.encodedPath
        if(checking.contains("login")) {
            return chain.proceed(request)
        } else {
            if(!token.isNullOrEmpty()) {
                // adding header
                val newRequest = request
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${token}")
                    .build()
                return chain.proceed(newRequest)
            }
        }
        return chain.proceed(request)
    }
}