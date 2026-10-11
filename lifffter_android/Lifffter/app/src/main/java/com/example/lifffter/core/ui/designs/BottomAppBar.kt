package com.example.lifffter.core.ui.designs

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.lifffter.R
import com.example.lifffter.core.navigation.Screen

@Composable
fun BottomAppBar(
    navController: NavController
) {
    NavigationBar {
        NavigationBarItem(
            onClick = {
                navController.navigate(Screen.DashboardScreen)
            },
            selected = true,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_calendar_clock_24),
                    contentDescription = "Workout History Tab",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.background
                    )
                )
            }
        )

        NavigationBarItem(
            onClick = { navController.navigate(Screen.RoutineScreen) },
            selected = true,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_sticky_note_2_24),
                    contentDescription = "Routine Tab",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )

        NavigationBarItem(
            onClick = { navController.navigate(Screen.WorkoutSessionScreen()) },
            selected = true,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_timer_24),
                    contentDescription = "Active Workout Tab",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )

        NavigationBarItem(
            onClick = { navController.navigate(Screen.ProfileScreen) },
            selected = true,
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile section",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
}