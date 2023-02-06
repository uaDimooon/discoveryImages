package com.dmytron.discoveryimages.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dmytron.discoveryimages.ImagesViewModel

@Composable
fun ImageDetailsView(imagesViewModel: ImagesViewModel, navController: NavController) {
    Column {
        TitleBar(imagesViewModel.detailsImage?.title ?: "") { navController.popBackStack() }
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imagesViewModel.detailsImage?.url,
            contentDescription = imagesViewModel.detailsImage?.title
        )
    }
}

@Composable
fun TitleBar(
    title: String,
    onNavigateBack: () -> Unit = {}
) {
    TopAppBar(title = { Text(title) }, navigationIcon = {
        IconButton(onClick = { onNavigateBack() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                modifier = Modifier,
                contentDescription = "back button"
            )
        }
    })
}