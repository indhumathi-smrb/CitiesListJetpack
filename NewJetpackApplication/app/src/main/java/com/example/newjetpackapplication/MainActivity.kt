package com.example.newjetpackapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newjetpackapplication.ui.theme.NewJetpackApplicationTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewJetpackApplicationTheme {
            val cities = loadCitiesFromAsset("cities.json")
            val groupedCities = groupCitiesByState(cities)
            CityListScreen(groupedCities = groupedCities)
        }
        }
    }
    // Function to parse the JSON string into a list of City objects
    private fun loadCitiesFromAsset(fileName: String): List<City> {
        val jsonString = loadJsonFromAsset(fileName) ?: return emptyList()

        val gson = Gson()
        val typeToken = object : TypeToken<List<City>>() {}.type
        return gson.fromJson(jsonString, typeToken)
    }
    // Function to load JSON from assets folder
    private fun loadJsonFromAsset(fileName: String): String? {
        return try {
            val inputStream = assets.open(fileName)
            val reader = InputStreamReader(inputStream)
            reader.readText()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error loading JSON file", e)
            null
        }
    }

    private fun groupCitiesByState(cities: List<City>): List<StateSection> {
        return cities.groupBy { it.admin_name }
            .map { (state, citiesInState) ->
                StateSection(state, citiesInState)
            }
            .sortedBy { it.state }
    }
}

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


// Composable for displaying each city's details
@Composable
fun CityCard(city: City) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // City name
            Text(text = city.city, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            // Admin Name
            //Text(text = "Admin Name: ${city.admin_name}", style = MaterialTheme.typography.bodySmall)
            // Country
            Text(text = "Country: ${city.country}", style = MaterialTheme.typography.bodySmall)
            // Coordinates
            Text(text = "Coordinates: (${city.lat}, ${city.lng})", style = MaterialTheme.typography.bodySmall)
            // Population
            Text(text = "Population: ${city.population}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CityListScreen(groupedCities = listOf(
        StateSection("New South Wales", listOf(
            City("Sydney", "-33.8678", "151.2100", "Australia", "AU", "New South Wales", "admin", "4840600", "4840600"),
            City("Newcastle", "-32.9283", "151.7817", "Australia", "AU", "New South Wales", "admin", "308308", "308308")
        )),
        StateSection("Victoria", listOf(
            City("Melbourne", "-37.8142", "144.9631", "Australia", "AU", "Victoria", "admin", "4529500", "4529500"),
            City("Geelong", "-38.1499", "144.3617", "Australia", "AU", "Victoria", "admin", "226000", "226000")
        ))
    ))
}