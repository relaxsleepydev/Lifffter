package com.example.lifffter.feature_dashboard.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifffter.core.ui.theme.LocalSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun DashboardTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Workout History",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
//                modifier = Modifier.padding(LocalSpacing.current.medium)
            )
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
            subtitleContentColor = MaterialTheme.colorScheme.surfaceBright,
            actionIconContentColor = Color.Black,
            navigationIconContentColor = Color.Black,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceBright
        )
    )
}