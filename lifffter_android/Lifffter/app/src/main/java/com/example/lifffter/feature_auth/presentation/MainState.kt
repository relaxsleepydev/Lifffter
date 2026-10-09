package com.example.lifffter.feature_auth.presentation

sealed class MainState {
    object Loading: MainState()
    object Authenticated: MainState()
    object UnAuthenticated: MainState()
}