package com.example.lifffter.feature_routines.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifffter.feature_routines.domain.model.Routine
import com.example.lifffter.feature_routines.domain.repository.RoutineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val repository: RoutineRepository
): ViewModel() {
    private val _state = MutableStateFlow(RoutineUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getRoutines().collect {
                _state.value = _state.value.copy(
                    // copy takes current state and only modifies what we want to change
                    // like here we changed routines to the new value
                    routines = it
                )
            }
        }
        onEvent(RoutineEvent.SyncRoutine)
    }

    fun onEvent(event: RoutineEvent) {
        if(event == RoutineEvent.AddDummyRoutine) {
            viewModelScope.launch {
                repository.insertRoutine(Routine(
                    id = UUID.randomUUID(),
                    name = "Leg Day",
                    targetMuscleGroup = "Legs"
                ))
            }
        }
        if(event == RoutineEvent.SyncRoutine) {
            viewModelScope.launch {
                // show the spinner
                _state.value = _state.value.copy(isLoading = true)

                // fetch the data
                repository.syncRoutine()

                // hide the spinner
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}