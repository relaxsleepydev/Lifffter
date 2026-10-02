package com.example.lifffter.feature_tracking.presentation.screens

import android.widget.CheckBox
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.lifffter.core.ui.theme.LocalSpacing
import com.example.lifffter.feature_tracking.domain.models.WorkoutSet
import com.example.lifffter.feature_tracking.presentation.viewmodel.WorkoutSessionEvent

@Composable
fun SetRow(
    set: WorkoutSet,
    onEvent: (WorkoutSessionEvent) -> Unit,
    setNumber: Int,
    prevStats: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = LocalSpacing.current.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${setNumber}",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = prevStats,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = set.weight.toString(),
            onValueChange = {
                val safeWeight = it.toFloatOrNull() ?: 0f
                onEvent(
                    WorkoutSessionEvent.UpdateSetWeight(set.id, weight = safeWeight)
                )
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = LocalSpacing.current.small),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        OutlinedTextField(
            value = set.reps.toString(),
            onValueChange = { newText ->
                val parsedReps = if(newText.isNotBlank()) 0 else newText.toIntOrNull() ?: 0
                onEvent(
                    WorkoutSessionEvent.UpdateSetReps(set.id, reps = parsedReps)
                )
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = LocalSpacing.current.small),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Checkbox(
            checked = set.isCompleted,
            onCheckedChange = { isChecked ->
                onEvent(
                    WorkoutSessionEvent.ToggleSetComplete(
                        set.id,
                        isChecked
                    )
                )
            },
            modifier = Modifier.weight(1f)
        )
    }
}