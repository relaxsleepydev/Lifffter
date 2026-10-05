package com.example.lifffter.feature_dashboard.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.workDataOf
import com.example.lifffter.R
import com.example.lifffter.core.ui.theme.LocalSpacing
import com.example.lifffter.core.ui.theme.darkColors
import com.example.lifffter.feature_dashboard.presentation.convertToDate
import com.example.lifffter.feature_dashboard.presentation.convertToDuration
import com.example.lifffter.feature_dashboard.presentation.convertToTime
import com.example.lifffter.feature_tracking.domain.models.WorkoutHistoryItem

@Composable
fun DashboardCard(
    item: WorkoutHistoryItem
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().border(
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        ),
        shape = RoundedCornerShape(24.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledContentColor = MaterialTheme.colorScheme.onBackground,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(LocalSpacing.current.medium)
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_fitness_center_24),
                contentDescription = "dumbbell icon"

            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.workoutTitle,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = convertToDate(item.date)
                )
            }
            Text(
                text = convertToTime(item.date),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(LocalSpacing.current.medium),
//            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Duration: ${convertToDuration(item.duration)}",
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = "Volume: ${item.totalVolume} lbs",
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Exercises: ${item.exerciseName.size}",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Sets: ${item.totalSets}   Reps: ${item.totalReps}",
                    style = MaterialTheme.typography.titleSmall
                )
//                Text(
//                    text = "Reps: ${item.totalReps}",
//                    style = MaterialTheme.typography.titleSmall
//                )
            }
        }
        OutlinedButton(
            onClick = { },
            modifier = Modifier.padding(LocalSpacing.current.small).fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "View Details"
            )
        }
    }
}
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
////            modifier = Modifier.weight(1f)
//        ) {
//            item.exerciseName.take(3).forEach {
//                Text(
//                    text = it,
//                    modifier = Modifier.weight(1f)
//                )
//            }
//        }
