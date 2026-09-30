package com.example.lifffter.core.di

import com.example.lifffter.feature_tracking.data.repository.ActiveWorkoutRepositoryImpl
import com.example.lifffter.feature_tracking.domain.repository.ActiveWorkoutRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkoutSessionModule {
    @Binds
    abstract fun provideWorkoutSessionRepository(impl: ActiveWorkoutRepositoryImpl): ActiveWorkoutRepository
}