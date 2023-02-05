package com.dmytron.discoveryimages.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import com.dmytron.discoveryimages.data.Image

@Composable
fun ImagesGrid(images: List<Image>) {
    LazyVerticalGrid(columns = GridCells.Fixed(5)) {
        items(images) { image ->
            ImageItem(image)

        }
    }
}
