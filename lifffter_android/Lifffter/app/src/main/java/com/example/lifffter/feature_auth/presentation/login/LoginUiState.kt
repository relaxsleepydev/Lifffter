package com.example.lifffter.feature_auth.presentation.login

sealed class LoginUiState {
    object Success: LoginUiState()
    object Loading: LoginUiState()
    object Idle: LoginUiState()
    data class Error(val message: String): LoginUiState()
}