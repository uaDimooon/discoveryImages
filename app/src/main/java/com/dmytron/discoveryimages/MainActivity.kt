package com.dmytron.discoveryimages

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.dmytron.discoveryimages.data.Repository
import com.dmytron.discoveryimages.ui.theme.DiscoveryImagesTheme

class MainActivity : ComponentActivity() {
    private val imagesViewModel: ImagesViewModel by viewModels { ImagesViewModel.Factory(repository()) }
    private val searchViewModel: SearchViewModel by viewModels { SearchViewModel.Factory(repository()) }

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiscoveryImagesTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Navigation(
                        rememberNavController(),
                        rememberScaffoldState(),
                        imageGridViewModel = imagesViewModel, searchViewModel
                    )
                }
            }
        }
    }

    private fun preferences(): SharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE)
    private fun repository(): Repository = Repository.flickrRepository(preferences())
}

private const val PREFERENCES = "discovery_shared_preferences"