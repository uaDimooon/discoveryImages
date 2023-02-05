package com.dmytron.discoveryimages

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import com.dmytron.discoveryimages.ui.ImagesGrid
import com.dmytron.discoveryimages.ui.SearchBar
import com.dmytron.discoveryimages.ui.theme.DiscoveryImagesTheme
import java.util.function.Consumer

class MainActivity : ComponentActivity() {
    private val photosViewModel: ImagesViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class,
        ExperimentalMaterial3Api::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photosViewModel.images.observe(this, Observer { images ->
            images.forEach(Consumer { image -> Log.e(MainActivity::class.java.name, image.url) })
        })
        setContent {
            DiscoveryImagesTheme {
                Scaffold(topBar = { SearchBar(searchText = "") }) {
                    mainContent()
                }
            }
        }
    }

    @Composable
    fun mainContent() = Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val images by photosViewModel.images.observeAsState(listOf())
        ImagesGrid(images = images)
    }
}