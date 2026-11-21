package com.example.lifffter.feature_exercise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifffter.feature_exercise.domain.model.Exercise
import com.example.lifffter.feature_exercise.domain.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
            repository.getExercises().collect { masterList ->
                _exercises.update { current ->
                    current.copy(
                        exercises = masterList,
                        filteredExercises = filterExercises(masterList, current.searchQuery)
                    )
                }
            }
        }
        onEvent(ExerciseEvent.SyncExercise)
    }

    fun onEvent(event: ExerciseEvent) {
        when(event) {
            is ExerciseEvent.OnSearchQueryChange -> {
                val query = event.query
                _exercises.update { current ->
                    current.copy(
                        searchQuery = query,
                        filteredExercises = filterExercises(current.exercises, query)
                    )
                }
            }

            is ExerciseEvent.SyncExercise, ExerciseEvent.Refresh -> {
                viewModelScope.launch {
                    _exercises.update { it.copy(isLoading = true) }
                    try {
                        repository.syncExercises()
                    } catch (e: Exception) {
                        _exercises.update { it.copy(error = e.message) }
                    }
                    _exercises.update { it.copy(isLoading = false) }
                }
            }

            is ExerciseEvent.OnExerciseClick -> { }
        }
    }

    private fun filterExercises(list: List<Exercise>, query: String): List<Exercise> {
        if (query.isBlank()) return list
        return list.filter { exercise ->
            exercise.name.contains(query, ignoreCase = true) ||
                    exercise.primaryMuscle.contains(query, ignoreCase = true)
        }
    }
}