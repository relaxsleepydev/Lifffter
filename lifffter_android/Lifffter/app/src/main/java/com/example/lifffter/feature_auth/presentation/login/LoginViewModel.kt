package com.example.lifffter.feature_auth.presentation.login

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifffter.core.security.DataStorePreferences
import com.example.lifffter.feature_auth.data.remote.authDto.LoginRequestDTO
import com.example.lifffter.feature_auth.data.remote.authapi.AuthAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authApi: AuthAPI,
    private val dataStorePreferences: DataStorePreferences
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading
            try {
                val requestBody = LoginRequestDTO(email, password)
                val response = authApi.loginUser(requestBody)
                dataStorePreferences.putSecurePreference(
                    stringPreferencesKey("token"),
                    response.token
                )
                _loginState.value = LoginUiState.Success
            } catch (e: Exception) {
                _loginState.value = LoginUiState.Error(e.message.toString())
            }
        }
    }
}