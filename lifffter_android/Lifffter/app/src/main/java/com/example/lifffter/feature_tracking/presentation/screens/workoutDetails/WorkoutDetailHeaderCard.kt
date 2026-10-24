package com.example.lifffter.feature_tracking.presentation.screens.workoutDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lifffter.feature_tracking.domain.models.WorkoutSession
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun WorkoutDetailHeaderCard(session: WorkoutSession) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy - hh:mm a", LocalLocale.current.platformLocale)
    val formattedDate = dateFormat.format(Date(session.startTime))

    val durationMinutes = if (session.endTime != null && session.endTime > session.startTime) {
        (session.endTime - session.startTime) / (1000 * 60)
    } else {
        0L
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = session.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                MetricColumn(label = "Duration", value = "$durationMinutes mins")
                MetricColumn(label = "Exercises", value = "${session.exercises.size}")
                MetricColumn(
                    label = "Total Sets",
                    value = "${session.exercises.sumOf { it.sets.size }}"
                )
            }
        }
    }
}