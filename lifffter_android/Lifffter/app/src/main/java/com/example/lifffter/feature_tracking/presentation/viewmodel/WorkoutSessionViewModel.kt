package com.example.lifffter.feature_tracking.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.lifffter.core.navigation.Screen
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
import kotlinx.coroutines.flow.update
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
        val route = savedStateHandle.toRoute<Screen.WorkoutSessionScreen>()
        val routineId = route.routineId

        Log.d("HYDRATION_TEST", "Passed ID from route: $routineId")

        viewModelScope.launch {
            var sessionName = "Active Workout"
            val parsedRoutineId = routineId?.let { UUID.fromString(it) }

            // 1. Fetch the routine data ONCE
            val routineData = if (routineId != null) {
                repositoryRoutine.getRoutineWithExerciseById(routineId)
            } else null

            Log.d("HYDRATION_TEST", "Routine Found: ${routineData?.routine?.name}")
            Log.d("HYDRATION_TEST", "Exercise Count: ${routineData?.exercises?.size}")

            // 2. Extract name if it exists
            if (routineData != null) {
                sessionName = routineData.routine.name
            }

            // 3. Push the name to the UI state immediately so the TextField populates
            _state.update { it.copy(sessionTitle = sessionName) }

            // 4. Create and insert the session
            val newSession = WorkoutSession(
                id = activeSessionId,
                routineId = parsedRoutineId,
                title = sessionName,
                startTime = System.currentTimeMillis(),
                endTime = null
            )
            repository.insertSession(newSession)

            // 5. Hydrate sets using the data we ALREADY fetched
            routineData?.exercises?.forEach { exercise ->
                repository.insertSet(
                    exerciseId = exercise.id,
                    sessionId = activeSessionId,
                    set = WorkoutSet(id = UUID.randomUUID())
                )
            }

            // 6. Collect live flow
            repository.getActiveSession(activeSessionId).collect { session ->
                _state.update { it.copy(workoutSession = session) }
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
            is WorkoutSessionEvent.UpdateSessionTitle -> {
                _state.update { it.copy(sessionTitle = event.title) }
                viewModelScope.launch {
                    repository.updateSessionTitle(activeSessionId, event.title)
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