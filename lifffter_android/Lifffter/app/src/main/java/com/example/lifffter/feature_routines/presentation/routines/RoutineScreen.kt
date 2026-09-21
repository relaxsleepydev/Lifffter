package com.example.lifffter.feature_routines.presentation.routines

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifffter.feature_routines.presentation.RoutineEvent
import com.example.lifffter.feature_routines.presentation.RoutineViewModel

@Composable
fun RoutineScreen(viewModel: RoutineViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold() { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error != null) {
                Text(
                    text = "Error in Loading Routines"
                )
            } else if (state.routines.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(state.routines) { routine ->
                        Column {
                            Text(
                                text = "Name of Routine: ${routine.name}"
                            )
                            Text(
                                text = "Muscle Group Targetted: ${routine.targetMuscleGroup}"
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "No routine Found"
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = { viewModel.onEvent(RoutineEvent.AddDummyRoutine) }
            ) {
                Text(text = "Add Dummy Routine")
            }
        }
    }
}