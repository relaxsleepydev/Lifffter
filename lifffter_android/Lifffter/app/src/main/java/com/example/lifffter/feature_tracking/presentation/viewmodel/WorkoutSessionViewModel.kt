package com.example.lifffter.feature_tracking.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifffter.feature_routines.domain.repository.RoutineRepository
import com.example.lifffter.feature_tracking.domain.models.WorkoutSession
import com.example.lifffter.feature_tracking.domain.models.WorkoutSet
import com.example.lifffter.feature_tracking.domain.repository.ActiveWorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class WorkoutSessionViewModel @Inject constructor(
    private val repository: ActiveWorkoutRepository,
    private val repositoryRoutine: RoutineRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(WorkoutSessionUiState())
    val state = _state.asStateFlow()
    val activeSessionId = UUID.randomUUID()

    init {
        val routineId = savedStateHandle.get<String>("routineId")
        if(routineId != null)
        {
            viewModelScope.launch {
                val routineData = repositoryRoutine.getRoutineWithExerciseById(routineId)
                routineData?.exercises?.forEach { exercise ->
                    repository.addExerciseToSession(activeSessionId, exercise.id)
                }
            }
        }
//        Log.d("LIFECYCLE_TEST", "ViewModel Created! Session ID: $activeSessionId")
        viewModelScope.launch {
            val newSession = WorkoutSession(
                id = activeSessionId,
                routineId = null,
                startTime = System.currentTimeMillis(),
                endTime = null
            )
            // With OnConflictStrategy.IGNORE in the DAO, this is now safe:
            repository.insertSession(newSession)

            // Collect your live flow
            repository.getActiveSession(activeSessionId).collect { session ->
                _state.value = _state.value.copy(
                    workoutSession = session
                )
            }
        }
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: WorkoutSessionEvent) {
        when(event) {
            is WorkoutSessionEvent.SyncSession -> {
                viewModelScope.launch {
                    repository.insertSession(
                        WorkoutSession(
                            id = activeSessionId,
                            routineId = UUID.randomUUID(),
                            startTime = System.currentTimeMillis(),
                            endTime = null
                        )
                    )
                }
            }
            is WorkoutSessionEvent.AddSet -> {
                viewModelScope.launch {
                    repository.insertSet(
                        exerciseId = event.exerciseId,
                        sessionId = activeSessionId,
                        set = WorkoutSet(id = UUID.randomUUID())
                    )
                }
            }
            is WorkoutSessionEvent.UpdateSetWeight -> {
                viewModelScope.launch {
                    repository.updateSetWeight(event.setId, event.weight)
                }
            }
            is WorkoutSessionEvent.FinishWorkout -> {
                viewModelScope.launch {
                    repository.finishAndSyncWorkout(activeSessionId)
                    _uiEvent.send(UiEvent.NavigateBack)
                }
            }
            is WorkoutSessionEvent.ToggleSetComplete -> {
                viewModelScope.launch {
                    repository.toggleSetComplete(event.setId, event.isCompleted)
                }
            }

            is WorkoutSessionEvent.UpdateSetReps -> {
                viewModelScope.launch {
                    repository.updateSetReps(event.setId, event.reps)
                }
            }

            is WorkoutSessionEvent.AddExercise -> {
                viewModelScope.launch {
                    repository.addExerciseToSession(activeSessionId, event.exerciseId)
                }
            }
        }
    }
}

sealed class UiEvent {
    object NavigateBack: UiEvent()
}