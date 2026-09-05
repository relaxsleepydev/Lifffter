package com.example.lifffter.core.di

import android.content.Context
import androidx.room.Room
import com.example.lifffter.core.database.LifffterDatabase
import com.example.lifffter.feature_routines.data.local.RoutineDAO
import com.example.lifffter.feature_routines.data.repository.RoutineRepositoryImpl
import com.example.lifffter.feature_routines.domain.repository.RoutineRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RoutineModule {
    @Binds
    abstract fun ProvideRoutineRepository(impl: RoutineRepositoryImpl): RoutineRepository
}