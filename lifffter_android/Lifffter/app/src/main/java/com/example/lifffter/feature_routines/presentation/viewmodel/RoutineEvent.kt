package com.example.lifffter.feature_routines.presentation.viewmodel

import java.util.UUID

sealed class RoutineEvent {
    data object Refresh: RoutineEvent()
    data class OnRoutineClick(val routineId: UUID): RoutineEvent()
    data object AddDummyRoutine: RoutineEvent()
    data object SyncRoutine: RoutineEvent()
}