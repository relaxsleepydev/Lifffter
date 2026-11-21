package com.example.lifffter.core.ui.designs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifffter.core.ui.theme.darkColors
import com.example.lifffter.core.ui.theme.lightColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    text: String,
    searchQuery: String = "",
    onQueryChange: (String) -> Unit = {},
    isSearchActive: Boolean = false,
    onToggleSearch: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            if(isSearchActive) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    placeholder = {
                        Text(
                            text = "Search Exercise",
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
            } else {
                Text(
                    text = text,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        actions = {
            IconButton(
                onClick = onToggleSearch,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.onBackground
                    )
            ) {
                Icon(
                    imageVector = if(isSearchActive) Icons.Default.Close else Icons.Default.Search,
                    contentDescription = if(isSearchActive) "Close Search" else "Search Button",
                    tint = Color.White,
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            subtitleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}