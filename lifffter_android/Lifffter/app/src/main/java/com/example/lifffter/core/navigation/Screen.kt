package com.example.lifffter.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object LoginScreen: Screen()

    @Serializable
    object HomeScreen: Screen()

    @Serializable
    object WorkoutScreen: Screen()

    @Serializable
    object RoutineScreen: Screen()
}