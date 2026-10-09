package com.example.lifffter.feature_profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifffter.core.database.LifffterDatabase
import com.example.lifffter.core.security.DataStorePreferences
import com.example.lifffter.core.security.SecurityUtil
import com.example.lifffter.feature_profile.presentation.events.ProfileEvent
import com.example.lifffter.feature_profile.presentation.states.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: DataStorePreferences,
    private val database: LifffterDatabase
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState(toShowDialog = false))
    val state = _state.asStateFlow()

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.OnShowLogoutDialog -> {
                _state.value = _state.value.copy(toShowDialog = true)
            }
            is ProfileEvent.OnConfirmLogoutDialog -> {
                _state.value = _state.value.copy(toShowDialog = false)

                viewModelScope.launch(Dispatchers.IO) {
                    authRepo.clearToken()
                    database.clearAllTables()
                }
            }
            is ProfileEvent.OnDismissLogoutDialog -> {
                _state.value = _state.value.copy(toShowDialog = false)
            }
        }
    }
}