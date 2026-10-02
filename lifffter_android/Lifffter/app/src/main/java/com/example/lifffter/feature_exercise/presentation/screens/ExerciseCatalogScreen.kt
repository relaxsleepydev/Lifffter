package com.example.lifffter.feature_exercise.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifffter.core.ui.designs.TopBar
import com.example.lifffter.feature_exercise.presentation.viewmodel.ExerciseEvent
import com.example.lifffter.feature_exercise.presentation.viewmodel.ExerciseViewModel

@Composable
fun ExerciseCatalogScreen(
    viewModel: ExerciseViewModel = hiltViewModel(),
    onExerciseClick: (String) -> Unit
) {
    val state by viewModel.exercises.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar(
                text = "Exercise Catalog"
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.exercises) { exercise ->
                    ExerciseCardItem(
                        exerciseName = exercise.name,
                        exerciseMuscle = exercise.primaryMuscle,
                        onExerciseClick = { onExerciseClick(exercise.id) }
                    )
                }
            }
            if(state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
