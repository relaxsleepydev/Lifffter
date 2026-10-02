package com.example.lifffter.feature_exercise.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifffter.feature_exercise.domain.model.Exercise
import com.example.lifffter.feature_exercise.domain.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
): ViewModel() {

    private val _exercises = MutableStateFlow(ExerciseUiState())
    val exercises = _exercises.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getExercises().collect { exercises ->
                Log.d("NetworkDebug", "Room emitted ${exercises.size} exercises to the ViewModel")
                _exercises.value = _exercises.value.copy(
                    exercises = exercises
                )
            }
        }
        onEvent(ExerciseEvent.SyncExercise)
    }

    fun onEvent(event: ExerciseEvent) {
        when(event) {
            is ExerciseEvent.SyncExercise, ExerciseEvent.Refresh -> {
                viewModelScope.launch {
                    // show spinner
                    _exercises.value = _exercises.value.copy(isLoading = true)

                    // fetch data
                    try {
                        Log.d("NetworkDebug", "Starting network fetch...")
                        repository.syncExercises()
                        Log.d("NetworkDebug", "Network fetch finished successfully!")
                    } catch (e: Exception) {
                        Log.e("NetworkDebug", "Sync failed: ${e.message}", e)
                    }

                    // hide spinner
                    _exercises.value = _exercises.value.copy(isLoading = false)
                }
            }
            is ExerciseEvent.OnExerciseClick -> {

            }
        }
    }
}