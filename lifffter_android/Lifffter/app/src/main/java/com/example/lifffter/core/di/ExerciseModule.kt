package com.example.lifffter.core.di

import com.example.lifffter.feature_exercise.data.repository.ExerciseRepositoryImpl
import com.example.lifffter.feature_exercise.domain.repository.ExerciseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExerciseModule {

    @Binds
    abstract fun provideExerciseRepository(impl: ExerciseRepositoryImpl): ExerciseRepository
}