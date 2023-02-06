package com.dmytron.discoveryimages

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dmytron.discoveryimages.ui.ImagesGrid
import com.dmytron.discoveryimages.ui.SearchHistoryView
import com.dmytron.discoveryimages.ui.rememberFlowWithLifecycle

enum class Destination(val target: String) {
    ImagesGrid(target = "images_grid"),
    Search(target = "search")
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun Navigation(
    navHostController: NavHostController,
    imageGridViewModel: ImagesViewModel,
    searchViewModel: SearchViewModel
) {

    NavHost(
        navController = navHostController,
        startDestination = Destination.ImagesGrid.target
    ) {

        composable(Destination.ImagesGrid.target) {
            val images by imageGridViewModel.images.observeAsState(listOf())
            val search by searchViewModel.searchState.collectAsState(initial = SearchState.Empty)
            ImagesGrid(images = images, term = search.activeSearch, navHostController)
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
