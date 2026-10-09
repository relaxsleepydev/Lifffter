package com.example.lifffter.feature_routines.presentation.routines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifffter.R
import com.example.lifffter.core.ui.theme.LocalSpacing
import com.example.lifffter.feature_exercise.data.local.ExerciseEntity

@Composable
fun RoutineExerciseCard(
    exercise: ExerciseEntity,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalSpacing.current.large),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(LocalSpacing.current.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = exercise.name
            )

            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
//                    painter = painterResource(R.drawable.outline_cancel_24),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete Exercise"
                )
            }
        }
    }
}