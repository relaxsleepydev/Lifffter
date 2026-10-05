package com.example.lifffter.feature_routines.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifffter.feature_exercise.domain.repository.ExerciseRepository
import com.example.lifffter.feature_routines.data.local.RoutineDAO
import com.example.lifffter.feature_routines.data.local.RoutineEntity
import com.example.lifffter.feature_routines.domain.repository.RoutineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

sealed class UiEvent{
    data object NavigateUp: UiEvent()
}

@HiltViewModel
class CreateRoutineViewModel @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val _routineState = MutableStateFlow(CreateRoutineState())
    val routineState = _routineState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: CreateRoutineEvent) {
        when(event) {
            is CreateRoutineEvent.OnSaveRoutine -> {
                viewModelScope.launch {
                    val currentState = _routineState.value

                    if(currentState.routineName.isBlank() || currentState.selectedExercises.isEmpty()) {
                        return@launch
                    }

                    val newRoutine = RoutineEntity(
                        id = UUID.randomUUID().toString(),
                        name = currentState.routineName,
                        targetMuscleGroup = currentState.targetMuscle
                    )

                    routineRepository.insertRoutineWithExercises(
                        routine = newRoutine,
                        exercises = currentState.selectedExercises
                    )

                    _uiEvent.emit(UiEvent.NavigateUp)
                }
            }

            is CreateRoutineEvent.OnNameChange -> {
                _routineState.update { current ->
                    current.copy(
                        routineName = event.name
                    )
                }
            }

            is CreateRoutineEvent.OnAddExercise -> {
                viewModelScope.launch {
                    val exercise = exerciseRepository.getExerciseById(event.exerciseId)
                    if(exercise != null)
                    {
                        _routineState.update { current ->
                            current.copy(selectedExercises = current.selectedExercises + exercise)
                        }
                    }
                }
            }

            is CreateRoutineEvent.OnRemoveExercise -> {
                _routineState.update { current ->
                    current.copy(
                        selectedExercises = current.selectedExercises.filter {
                            it.id != event.exerciseId
                        }
                    )
                }
            }

            is CreateRoutineEvent.OnTargetMuscleChange -> {
                _routineState.update { current ->
                    current.copy(
                        targetMuscle = event.targetMuscleGroup
                    )
                }
            }
        }
    }
}