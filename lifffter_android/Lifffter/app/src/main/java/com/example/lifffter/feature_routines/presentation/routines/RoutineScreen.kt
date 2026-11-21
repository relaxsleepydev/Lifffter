package com.example.lifffter.feature_routines.presentation.routines

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
        topBar = { RoutineTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateCreateRoutine,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                    shape = RoundedCornerShape(12.dp)
                ),
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Routine"
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = "Add Routine"
                    )
                }
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
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.routines) { routine ->
                            RoutineCard(
                                name = routine.name,
                                targetMuscle = routine.targetMuscleGroup,
                                onClick = {
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