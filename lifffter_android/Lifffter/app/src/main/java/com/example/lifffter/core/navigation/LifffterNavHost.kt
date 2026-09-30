package com.example.lifffter.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lifffter.feature_routines.presentation.HomeScreen
import com.example.lifffter.feature_auth.presentation.login.LoginScreen
import com.example.lifffter.feature_exercise.presentation.screens.ExerciseCatalogScreen
import com.example.lifffter.feature_routines.presentation.routines.RoutineScreen
import com.example.lifffter.feature_routines.presentation.workout.WorkoutScreen
import com.example.lifffter.feature_tracking.presentation.screens.WorkoutSessionScreen
import com.example.lifffter.feature_tracking.presentation.viewmodel.WorkoutSessionViewModel

@Composable
fun LifffterNavHost() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.WorkoutSessionScreen) {
        composable<Screen.LoginScreen> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.ExerciseCatalogScreen)
                }
            )
        }

        composable<Screen.HomeScreen> {
            HomeScreen()
        }

        composable<Screen.RoutineScreen> {
            RoutineScreen()
        }

        composable<Screen.WorkoutSessionScreen> {
            val viewModel = hiltViewModel<WorkoutSessionViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            WorkoutSessionScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }

        composable<Screen.ExerciseCatalogScreen> {
            ExerciseCatalogScreen()
        }
    }
}