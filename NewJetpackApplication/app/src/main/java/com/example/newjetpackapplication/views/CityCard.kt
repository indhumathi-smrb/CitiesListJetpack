package com.example.newjetpackapplication.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newjetpackapplication.models.City

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
