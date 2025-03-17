package com.example.newjetpackapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.newjetpackapplication.ui.theme.NewJetpackApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewJetpackApplicationTheme {
                val viewModel : CityViewModel by viewModels()
                val groupedCities by viewModel.groupedCities.collectAsState()

                CityListScreen(groupedCities = groupedCities)
            }
        }
    }
}

