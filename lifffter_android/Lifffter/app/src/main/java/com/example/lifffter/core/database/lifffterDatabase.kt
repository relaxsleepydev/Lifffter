package com.example.lifffter.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lifffter.feature_routines.data.local.RoutineDAO
import com.example.lifffter.feature_routines.data.local.RoutineEntity

@Database(
    entities = [RoutineEntity::class],
    version = 1
)
abstract class LifffterDatabase: RoomDatabase() {
    abstract fun RoutineDao(): RoutineDAO
}