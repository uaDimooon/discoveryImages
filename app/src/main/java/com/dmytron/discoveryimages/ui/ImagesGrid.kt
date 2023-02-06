package com.dmytron.discoveryimages.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dmytron.discoveryimages.Destination
import com.dmytron.discoveryimages.data.Image

@Composable
fun ImagesGrid(images: List<Image>, navHostController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(searchBarClick = {
            navHostController.navigate(route = Destination.Search.target)
        })
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(images) { image ->
                ImageItem(image)
            }
        }
    }
}

@Composable
fun SearchBar(searchBarClick: () -> Unit) {
    TopAppBar(title = { Text("Let's Discover") }, actions = {
        IconButton(
            modifier = Modifier,
            onClick = { searchBarClick() }) {
            Icon(
                Icons.Filled.Search,
                contentDescription = "search icon"
            )
        }
    })
}
