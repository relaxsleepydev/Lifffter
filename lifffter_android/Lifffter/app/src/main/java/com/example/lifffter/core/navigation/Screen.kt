package com.example.lifffter.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object LoginScreen: Screen()

    @Serializable
    object HomeScreen: Screen()

    @Serializable
    object WorkoutSessionScreen: Screen()

    @Serializable
    object RoutineScreen: Screen()

    @Serializable
    object ExerciseCatalogScreen: Screen()
    @Serializable
    object DashboardScreen: Screen()
}