package com.example.lifffter

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifffter.core.security.DataStorePreferences
import com.example.lifffter.feature_auth.presentation.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStorePreferences: DataStorePreferences
) : ViewModel() {
    val state = dataStorePreferences.getSecurePreference(stringPreferencesKey("token"), "").map { token ->
        if(token.isNotBlank()) {
            MainState.Authenticated
        }
        else
        {
            MainState.UnAuthenticated
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainState.Loading
    )
}