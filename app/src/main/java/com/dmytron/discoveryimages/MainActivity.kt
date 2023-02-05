package com.dmytron.discoveryimages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dmytron.discoveryimages.data.source.DummyImageSource
import com.dmytron.discoveryimages.ui.ImagesGrid
import com.dmytron.discoveryimages.ui.theme.DiscoveryImagesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiscoveryImagesTheme {
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
    ImagesGrid(images = DummyImageSource().fetch())
}
    