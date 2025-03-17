package com.example.newjetpackapplication.models

// To group cities by state
data class StateSection(
    val state: String,
    val cities: List<City>
)