package com.example.newjetpackapplication.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.newjetpackapplication.models.City
import com.example.newjetpackapplication.models.StateSection

// DefaultPreview.kt
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