package com.example.lifffter.feature_tracking.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifffter.feature_tracking.domain.models.WorkoutSession
import com.example.lifffter.feature_tracking.domain.models.WorkoutSet
import com.example.lifffter.feature_tracking.domain.repository.ActiveWorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class WorkoutSessionViewModel @Inject constructor(
    private val repository: ActiveWorkoutRepository
): ViewModel() {
    private val _state = MutableStateFlow(WorkoutSessionUiState())
    val state = _state.asStateFlow()
    val activeSessionId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")

    init {
        viewModelScope.launch {
            repository.getActiveSession(activeSessionId).collect {
                _state.value = _state.value.copy(workoutSession = it)
            }
        }
    }

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
                    repository.deleteSession(activeSessionId)
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
        }
    }
}