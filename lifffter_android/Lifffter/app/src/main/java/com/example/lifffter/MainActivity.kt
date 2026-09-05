package com.example.lifffter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.lifffter.core.ui.theme.LifffterTheme
import com.example.lifffter.feature_routines.presentation.RoutineScreen
import com.example.lifffter.feature_routines.presentation.RoutineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifffterTheme {
                val viewModel: RoutineViewModel = hiltViewModel()
                RoutineScreen(viewModel)
            }
        }
    }
}

