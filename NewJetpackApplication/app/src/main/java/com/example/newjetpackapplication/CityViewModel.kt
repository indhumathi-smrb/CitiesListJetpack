package com.example.newjetpackapplication

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    application: MyApplication,
    private val gson: Gson
) : AndroidViewModel(application) {

    private val _groupedCities = MutableStateFlow<List<StateSection>>(emptyList())
    val groupedCities: StateFlow<List<StateSection>> = _groupedCities

    init {
        viewModelScope.launch {
            val cities = loadCitiesFromAsset("cities.json")
            val groupedCities = groupCitiesByState(cities)
            _groupedCities.value = groupedCities
        }
    }

    private suspend fun loadCitiesFromAsset(fileName: String): List<City> {
        val jsonString = loadJsonFromAsset(fileName) ?: return emptyList()
        return gson.fromJson(jsonString, object : TypeToken<List<City>>() {}.type)
    }

    private suspend fun loadJsonFromAsset(fileName: String): String? {
        return try {
            val inputStream = getApplication<Application>().assets.open(fileName)
            val reader = InputStreamReader(inputStream)
            reader.readText()
        } catch (e: Exception) {
            Log.e("CityViewModel", "Error loading JSON file", e)
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