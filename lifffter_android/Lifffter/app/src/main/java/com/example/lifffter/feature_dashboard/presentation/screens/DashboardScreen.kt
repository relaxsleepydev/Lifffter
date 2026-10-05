package com.example.lifffter.feature_dashboard.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.lifffter.core.ui.designs.BottomAppBar
import com.example.lifffter.feature_dashboard.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val historyState by viewModel.workoutHistory.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { DashboardTopBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // If empty, show a cool "Let's get lifting" empty state here!

            items(items = historyState) { item ->
                DashboardCard(item)
            }
        }
    }
}