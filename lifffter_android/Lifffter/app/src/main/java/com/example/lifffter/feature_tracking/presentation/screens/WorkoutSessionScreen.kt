package com.example.lifffter.feature_tracking.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.lifffter.core.ui.theme.LocalSpacing
import com.example.lifffter.feature_tracking.presentation.viewmodel.UiEvent
import com.example.lifffter.feature_tracking.presentation.viewmodel.WorkoutSessionEvent
import com.example.lifffter.feature_tracking.presentation.viewmodel.WorkoutSessionUiState
import com.example.lifffter.feature_tracking.presentation.viewmodel.WorkoutSessionViewModel

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
                onBackClick = onNavigateBack
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
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LocalSpacing.current.small)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            shape = RoundedCornerShape(
                                20.dp
                            )
                        ).background(
                            color = MaterialTheme.colorScheme.background
                        ),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        disabledContentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text(
                        text = "+ Add Exercise"
                    )
                }
            }
        }
    }
}