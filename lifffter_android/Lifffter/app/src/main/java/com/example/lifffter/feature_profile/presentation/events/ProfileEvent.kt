package com.example.lifffter.feature_profile.presentation.events

sealed class ProfileEvent {
    object OnShowLogoutDialog: ProfileEvent()
    object OnDismissLogoutDialog: ProfileEvent()
    object OnConfirmLogoutDialog: ProfileEvent()
}