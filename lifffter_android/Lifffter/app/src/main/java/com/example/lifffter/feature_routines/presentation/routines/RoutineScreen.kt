package com.example.lifffter.feature_routines.presentation.routines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifffter.feature_routines.presentation.viewmodel.RoutineEvent
import com.example.lifffter.feature_routines.presentation.viewmodel.RoutineViewModel

@Composable
fun RoutineScreen(
    onNavigateCreateRoutine: () -> Unit,
    viewModel: RoutineViewModel = hiltViewModel(),
    onRoutineClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateCreateRoutine) {
                Text(modifier = Modifier.padding(horizontal = 16.dp), text = "Add Routine")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null -> {
                    Text(
                        text = "Error in Loading Routines",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.routines.isEmpty() -> {
                    Text(
                        text = "No routines found. Create one!",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp), // Breathing room around the list
                        verticalArrangement = Arrangement.spacedBy(12.dp) // Space between cards
                    ) {
                        items(state.routines) { routine ->
                            // 2. Extract the item into a reusable Card component
                            RoutineCard(
                                name = routine.name,
                                targetMuscle = routine.targetMuscleGroup,
                                onClick = {
                                    // 3. Trigger your navigation and pass the ID!
                                    // (Make sure to use .toString() if your routine.id is a UUID)
                                    onRoutineClick(routine.id.toString())
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoutineCard(
    name: String,
    targetMuscle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // This makes the entire card tappable
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = targetMuscle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}