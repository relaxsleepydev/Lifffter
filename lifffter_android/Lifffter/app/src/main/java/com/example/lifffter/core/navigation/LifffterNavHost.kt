package com.example.lifffter.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lifffter.MainViewModel
import com.example.lifffter.feature_routines.presentation.HomeScreen
import com.example.lifffter.feature_auth.presentation.login.LoginScreen
import com.example.lifffter.feature_exercise.presentation.screens.ExerciseCatalogScreen
import com.example.lifffter.feature_routines.presentation.routines.RoutineScreen
import com.example.lifffter.feature_tracking.presentation.screens.WorkoutSessionScreen
import com.example.lifffter.feature_tracking.presentation.viewmodel.WorkoutSessionEvent
import com.example.lifffter.feature_tracking.presentation.viewmodel.WorkoutSessionViewModel
import com.example.lifffter.core.navigation.Screen.DashboardScreen
import com.example.lifffter.core.ui.designs.BottomAppBar
import com.example.lifffter.feature_auth.presentation.MainState
import com.example.lifffter.feature_dashboard.presentation.screens.DashboardScreen
import com.example.lifffter.feature_dashboard.presentation.viewmodel.DashboardViewModel
import com.example.lifffter.feature_exercise.presentation.viewmodel.ExerciseViewModel
import com.example.lifffter.feature_routines.presentation.routines.CreateRoutineScreen
import com.example.lifffter.feature_routines.presentation.viewmodel.CreateRoutineEvent
import com.example.lifffter.feature_routines.presentation.viewmodel.CreateRoutineViewModel
import com.example.lifffter.feature_routines.presentation.viewmodel.RoutineViewModel

@Composable
fun LifffterNavHost() {

    val navController = rememberNavController()
    val currentState by navController.currentBackStackEntryAsState()

    val mainViewModel = hiltViewModel<MainViewModel>()
    val state by mainViewModel.state.collectAsStateWithLifecycle()

    val startDestination = when(state) {
        is MainState.Authenticated -> DashboardScreen
        is MainState.Loading -> null
        is MainState.UnAuthenticated -> Screen.LoginScreen
    }

    if(startDestination == null)
    {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    else
    {
        Scaffold(
            topBar = { },
            bottomBar = {
                if (
                    currentState?.destination?.hasRoute<Screen.HomeScreen>() == true ||
                    currentState?.destination?.hasRoute<Screen.RoutineScreen>() == true ||
                    currentState?.destination?.hasRoute<DashboardScreen>() == true
                ) {
                    BottomAppBar(
                        navController = navController
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<Screen.LoginScreen> {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate(DashboardScreen) {
                                popUpTo(Screen.LoginScreen) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }

                composable<Screen.HomeScreen> {
                    HomeScreen()
                }

                composable<Screen.RoutineScreen> {
                    val viewModel = hiltViewModel<RoutineViewModel>()
                    RoutineScreen(
                        viewModel = viewModel,
                        onNavigateCreateRoutine = {
                            navController.navigate(Screen.CreateRoutineScreen) {
                                popUpTo(Screen.RoutineScreen) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }

                composable<Screen.WorkoutSessionScreen> { backStackEntry ->
                    val viewModel = hiltViewModel<WorkoutSessionViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    val selectedId = backStackEntry.savedStateHandle.get<String>("selected_exercise_id")

                    LaunchedEffect(selectedId) {
                        if (selectedId != null) {
                            try {
                                viewModel.onEvent(WorkoutSessionEvent.AddExercise(selectedId))
                            } catch (e: Exception) {

                            }
                            backStackEntry.savedStateHandle.remove<String>("selected_exercise_id")
                        }
                    }

                    WorkoutSessionScreen(
                        state = state,
                        onEvent = viewModel::onEvent,
                        viewModel = viewModel,
                        onNavigateBack = {
                            navController.navigate(DashboardScreen) {
                                popUpTo(DashboardScreen) {
                                    inclusive = false
                                }
                            }
                        },
                        onNavigateToCatalog = {
                            navController.navigate(Screen.ExerciseCatalogScreen)
                        }
                    )
                }

                composable<Screen.ExerciseCatalogScreen> {
                    ExerciseCatalogScreen(
                        viewModel = hiltViewModel<ExerciseViewModel>(),
                        onExerciseClick = { exerciseId ->
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                "selected_exercise_id",
                                exerciseId
                            )
                            navController.popBackStack()
                        }
                    )
                }

                composable<DashboardScreen> {
                    DashboardScreen(viewModel = hiltViewModel<DashboardViewModel>())
                }


                // trailing lambda is providing the exact navBackStackEntry to the composable
                composable<Screen.CreateRoutineScreen> { backStackEntry ->
                    val viewModel = hiltViewModel<CreateRoutineViewModel>()
                    val selectedExerciseId =
                        backStackEntry.savedStateHandle.get<String>("selected_exercise_id")
                    LaunchedEffect(selectedExerciseId) {
                        if (selectedExerciseId != null) {
                            viewModel.onEvent(CreateRoutineEvent.OnAddExercise(selectedExerciseId))
                            backStackEntry.savedStateHandle.remove<String>("selected_exercise_id")
                        }
                    }

                    CreateRoutineScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onNavigateCatalog = { navController.navigate(Screen.ExerciseCatalogScreen) }
                    )
                }
            }
        }
    }
}