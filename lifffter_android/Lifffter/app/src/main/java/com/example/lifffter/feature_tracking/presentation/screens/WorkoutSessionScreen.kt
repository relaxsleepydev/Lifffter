package com.example.lifffter.feature_tracking.presentation.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.lifffter.core.ui.theme.LocalSpacing
import com.example.lifffter.feature_tracking.domain.models.ActiveExercise
import com.example.lifffter.feature_tracking.presentation.viewmodel.UiEvent
import com.example.lifffter.feature_tracking.presentation.viewmodel.WorkoutSessionEvent
import com.example.lifffter.feature_tracking.presentation.viewmodel.WorkoutSessionUiState
import com.example.lifffter.feature_tracking.presentation.viewmodel.WorkoutSessionViewModel
import java.util.UUID

@Composable
fun WorkoutSessionScreen(
    state: WorkoutSessionUiState,
    onEvent: (WorkoutSessionEvent) -> Unit,
    viewModel: WorkoutSessionViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToCatalog: () -> Unit
) {

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when(event)
            {
                is UiEvent.NavigateBack -> onNavigateBack()
            }
        }
    }
    Scaffold(
        topBar = {
            WorkoutTopBar(
                onFinishClick = {
                    onEvent(WorkoutSessionEvent.FinishWorkout)
                },
                onBackClick = { }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            if(state.loading) {
                item {
                    CircularProgressIndicator()
                }
            }
            if (state.workoutSession != null) {
                items(
                    items = state.workoutSession.exercises,
                    key = { it.exerciseId }
                ) { exercise ->
                    WorkoutCard(
                        exercise = exercise,
                        onEvent = onEvent
                    )
                }
            }

            item {
                Button(
                    onClick = {
                        onNavigateToCatalog()
//                        onEvent(WorkoutSessionEvent.AddExercise(UUID.randomUUID()))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LocalSpacing.current.small)
                ) {
                    Text(
                        text = "+ Add Exercise"
                    )
                }
            }
        }
    }
}
// now list all the things we did from the starting of my idea of adding exercise from catalog in workoutscreen till now