package com.example.lifffter.feature_dashboard.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.lifffter.core.ui.designs.BottomAppBar
import com.example.lifffter.feature_dashboard.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onWorkoutClick: (String) -> Unit
) {
    val historyState by viewModel.workoutHistory.collectAsStateWithLifecycle()

    if(historyState.isEmpty())
    {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background
            )
        ) {
            Text(
                text = "Let's Start Lifting !"
            )
        }
    }

    Scaffold(
        topBar = { DashboardTopBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = historyState) { item ->
                DashboardCard(
                    item = item,
                    modifier = Modifier.clickable{
                        onWorkoutClick(item.sessionId.toString())
                    },
                    onViewDetailsClick = {
                        onWorkoutClick(item.sessionId.toString())
                    }
                )
            }
        }
    }
}