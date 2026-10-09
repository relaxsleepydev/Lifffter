package com.example.lifffter.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object LoginScreen: Screen()

    @Serializable
    object HomeScreen: Screen()

    @Serializable
    data class WorkoutSessionScreen(val routineId: String? = null): Screen()

    @Serializable
    object RoutineScreen: Screen()

    @Serializable
    data object CreateRoutineScreen: Screen()

    @Serializable
    object ExerciseCatalogScreen: Screen()
    @Serializable
    object DashboardScreen: Screen()
}