package com.example.lifffter.feature_routines.presentation

import java.util.UUID

sealed class RoutineEvent {
    data object Refresh: RoutineEvent()
    data class OnRoutineClick(val routineId: UUID): RoutineEvent()
    object AddDummyRoutine: RoutineEvent()
    object SyncRoutine: RoutineEvent()
}