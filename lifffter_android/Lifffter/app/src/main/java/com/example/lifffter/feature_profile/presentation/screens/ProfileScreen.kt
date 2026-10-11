package com.example.lifffter.feature_profile.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifffter.feature_profile.presentation.events.ProfileEvent
import com.example.lifffter.feature_profile.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ProfileTopBar(
                onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Picture",
                modifier = Modifier.size(100.dp)
            )

            TextButton(
                onClick = {
                    viewModel.onEvent(ProfileEvent.OnShowLogoutDialog)
                }
            ) {
                Text(
                    text = "Log Out"
                )
            }
        }
        if (state.toShowDialog) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.onEvent(ProfileEvent.OnDismissLogoutDialog)
                },
                title = {
                    Text(
                        text = "Log Out"
                    )
                },
                text = {
                    Text(
                        text = "Are you sure you want to log out? All offline data will be cleared."
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.onEvent(ProfileEvent.OnConfirmLogoutDialog)
                        }
                    ) {
                        Text(
                            text = "Yes, Log Out"
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            viewModel.onEvent(ProfileEvent.OnDismissLogoutDialog)
                        }
                    ) {
                        Text(
                            text = "Cancel"
                        )
                    }
                }
            )
        }
    }
}