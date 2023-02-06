package com.dmytron.discoveryimages

import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dmytron.discoveryimages.ui.ImagesGrid
import com.dmytron.discoveryimages.ui.SearchHistoryView

enum class Destination(val target: String) {
    ImagesGrid(target = "images_grid"),
    Search(target = "search")
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun Navigation(
    navHostController: NavHostController,
    scaffoldState: ScaffoldState,
    imageGridViewModel: ImagesViewModel,
    searchViewModel: SearchViewModel
) {

    NavHost(
        navController = navHostController,
        startDestination = Destination.ImagesGrid.target
    ) {

        composable(Destination.ImagesGrid.target) {
            val images by imageGridViewModel.images.observeAsState(listOf())
            ImagesGrid(images = images, navHostController)
        }

        composable(Destination.Search.target) {
            SearchHistoryView(
                navController = navHostController,
                viewModel = searchViewModel,
                imagesViewModel = imageGridViewModel
            )
        }
    }
}