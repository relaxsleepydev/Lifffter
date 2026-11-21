package com.example.lifffter.feature_tracking.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lifffter.core.ui.theme.LocalSpacing
import com.example.lifffter.feature_tracking.domain.models.ActiveExercise
import com.example.lifffter.feature_tracking.presentation.viewmodel.WorkoutSessionEvent

@Composable
fun WorkoutCard(
    exercise: ActiveExercise,
    onEvent: (WorkoutSessionEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalSpacing.current.medium),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledContentColor = MaterialTheme.colorScheme.onBackground
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
        )
    ) {
        Column(
            modifier = Modifier.padding(LocalSpacing.current.large)
        ) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(LocalSpacing.current.small))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sets",
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Prev",
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Lbs",
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Reps",
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.padding(LocalSpacing.current.small))

            Column() {
                exercise.sets.forEachIndexed{ index, set ->
                    SetRow(
                        setNumber = index + 1,
                        set = set,
                        onEvent = onEvent,
                        prevStats = "--"
                    )
                }
            }

            TextButton(
                onClick = {
                    onEvent(
                        WorkoutSessionEvent.AddSet(
                            exercise.exerciseId
                        )
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = MaterialTheme.colorScheme.onBackground,
                    disabledContainerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(
                    text = "+ Add Set"
                )
            }
        }
    }
}