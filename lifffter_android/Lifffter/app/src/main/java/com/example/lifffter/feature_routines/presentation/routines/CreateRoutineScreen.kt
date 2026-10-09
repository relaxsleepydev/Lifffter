package com.example.lifffter.feature_routines.presentation.routines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.lifffter.feature_routines.presentation.viewmodel.CreateRoutineViewModel
import com.example.lifffter.feature_routines.presentation.viewmodel.UiEvent
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifffter.feature_routines.presentation.viewmodel.CreateRoutineEvent
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import com.example.lifffter.core.ui.theme.LocalSpacing

@Composable
fun CreateRoutineScreen(
    viewModel: CreateRoutineViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateCatalog: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateUp -> onNavigateBack()
            }
        }
    }

    val routineState by viewModel.routineState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CreateRoutineTopBar(
                onBackClick = onNavigateBack,
                onSaveClick = { viewModel.onEvent(CreateRoutineEvent.OnSaveRoutine) },
                isSaveEnabled = routineState.routineName.isNotBlank() && routineState.selectedExercises.isNotEmpty()
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(LocalSpacing.current.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "ROUTINE DETAILS"
            )

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

            OutlinedTextField(
                value = routineState.routineName,
                onValueChange = {
                    viewModel.onEvent(CreateRoutineEvent.OnNameChange(it))
                },
                label = {
                    Text(
                        text = "Routine Name"
                    )
                },
            )

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

            OutlinedTextField(
                value = routineState.targetMuscle,
                onValueChange = {
                    viewModel.onEvent(CreateRoutineEvent.OnTargetMuscleChange(it))
                },
                label = {
                    Text(
                        text = "Target Muscle Group"
                    )
                }
            )

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

            Text(
                text = "EXERCISES: ${routineState.selectedExercises.size}"
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(routineState.selectedExercises) { exercise ->
                    RoutineExerciseCard(
                        exercise = exercise,
                        onDeleteClick = {
                            viewModel.onEvent(CreateRoutineEvent.OnRemoveExercise(exercise.id))
                        }
                    )
                }
            }

            OutlinedButton(
                onClick = onNavigateCatalog
            ) {
                Text(
                    text = "+  ADD EXERCISE"
                )
            }
        }
    }
}