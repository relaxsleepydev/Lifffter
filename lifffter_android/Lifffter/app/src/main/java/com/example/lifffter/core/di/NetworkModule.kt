package com.example.lifffter.core.di

import android.content.Context
import com.example.lifffter.core.network.AuthInterceptor
import com.example.lifffter.core.security.DataStorePreferences
import com.example.lifffter.core.security.SecurityUtil
import com.example.lifffter.feature_auth.data.remote.authapi.AuthAPI
import com.example.lifffter.feature_routines.data.remote.api.RoutineAPI
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSecurityUtil(): SecurityUtil {
        return SecurityUtil()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideDataStorePreferences(
        @ApplicationContext context: Context,
        securityUtil: SecurityUtil,
        gson: Gson
    ): DataStorePreferences {
        return DataStorePreferences(
            context,
            securityUtil,
            gson
        )
    }

    @Provides
    @Singleton
    fun provideInterceptor(
        dataStorePreferences: DataStorePreferences
    ): AuthInterceptor {
        return AuthInterceptor(dataStorePreferences)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://10.0.2.2:3000/") // ip should be valid, check that before finalizing
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRoutineApi(retrofit: Retrofit): RoutineAPI {
        return retrofit.create(RoutineAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }
}