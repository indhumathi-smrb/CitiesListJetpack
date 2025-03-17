package com.example.newjetpackapplication.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newjetpackapplication.models.StateSection


@Composable
fun CityListScreen(groupedCities: List<StateSection>) {
    var isReversed by remember { mutableStateOf(true) }
    var citiesState by remember { mutableStateOf(groupedCities) }

    // Reverse the cities when button is clicked
    val reversedCities = if (isReversed) {
        citiesState.map { stateSection ->
            stateSection.copy(cities = stateSection.cities.reversed())
        }
    } else {
        citiesState
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Button to reverse the list order
        Button(
            onClick = {
                isReversed = !isReversed
                citiesState = if (isReversed) {
                    citiesState.map { stateSection ->
                        stateSection.copy(cities = stateSection.cities.reversed())
                    }
                } else {
                    citiesState
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = if (isReversed) "Revert List Order" else "Reverse List Order")
        }
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            reversedCities.forEach { stateSection ->
                item {
                    var isExpanded by remember { mutableStateOf(false) }
                    // State Name Section (Collapsible)
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween, // To position the icon at the end
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stateSection.state,
                                style = MaterialTheme.typography.headlineLarge
                            )
                            IconButton(onClick = { isExpanded = !isExpanded }) {
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                                )
                            }
                        }

                        Divider()
                        // Toggle for collapsible section

                        // If expanded, show the cities
                        if (isExpanded) {
                            stateSection.cities.forEach { city ->
                                CityCard(city)
                            }
                        }
                    }
                }
            }
        }
    }
}