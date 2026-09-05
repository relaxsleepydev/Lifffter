package com.example.lifffter.core.di

import android.content.Context
import androidx.room.Room
import com.example.lifffter.core.database.LifffterDatabase
import com.example.lifffter.feature_routines.data.local.RoutineDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LifffterDatabase {
        return Room.databaseBuilder(
            context = context,
            LifffterDatabase::class.java,
            "lifffter_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: LifffterDatabase): RoutineDAO {
        return database.RoutineDao()
    }
}