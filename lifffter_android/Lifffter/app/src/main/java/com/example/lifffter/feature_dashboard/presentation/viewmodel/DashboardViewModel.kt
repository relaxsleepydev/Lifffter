package com.example.lifffter.feature_dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifffter.feature_tracking.data.mapper.toDomain
import com.example.lifffter.feature_tracking.domain.models.WorkoutHistoryItem
import com.example.lifffter.feature_tracking.domain.repository.ActiveWorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: ActiveWorkoutRepository,
) : ViewModel() {
    val workoutHistory = repository.getWorkoutHistory().map { mapData ->
        mapData.entries.map { entry ->
            entry.toDomain() }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}