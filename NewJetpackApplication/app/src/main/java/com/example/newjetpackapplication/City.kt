package com.example.newjetpackapplication

data class City(
    val city: String,
    val lat: String,
    val lng: String,
    val country: String,
    val iso2: String,
    val admin_name: String,
    val capital: String,
    val population: String,
    val population_proper: String
)

// To group cities by state
data class StateSection(
    val state: String,
    val cities: List<City>
)