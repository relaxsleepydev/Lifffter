package com.example.lifffter.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lifffter.feature_routines.presentation.HomeScreen
import com.example.lifffter.feature_auth.presentation.login.LoginScreen
import com.example.lifffter.feature_routines.presentation.routines.RoutineScreen
import com.example.lifffter.feature_routines.presentation.workout.WorkoutScreen

@Composable
fun LifffterNavHost() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen) {
        composable<Screen.LoginScreen> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.HomeScreen)
                }
            )
        }

        composable<Screen.HomeScreen> {
            HomeScreen()
        }

        composable<Screen.RoutineScreen> {
            RoutineScreen()
        }

        composable<Screen.WorkoutScreen> {
            WorkoutScreen()
        }
    }
}