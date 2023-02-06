package com.dmytron.discoveryimages

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
import com.dmytron.discoveryimages.ui.theme.DiscoveryImagesTheme

class MainActivity : ComponentActivity() {
    private val imagesViewModel: ImagesViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

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
}