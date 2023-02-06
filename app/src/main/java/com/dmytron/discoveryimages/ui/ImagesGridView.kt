package com.dmytron.discoveryimages.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dmytron.discoveryimages.Destination
import com.dmytron.discoveryimages.ImagesViewModel
import com.dmytron.discoveryimages.SearchState
import com.dmytron.discoveryimages.SearchViewModel
import com.dmytron.discoveryimages.data.Image

@Composable
fun ImagesGrid(
    viewModel: ImagesViewModel,
    searchViewModel: SearchViewModel,
    navHostController: NavHostController
) {
    val images by viewModel.images.observeAsState(listOf())
    val search by searchViewModel.searchState.collectAsState(initial = SearchState.Empty)

    Column(modifier = Modifier.fillMaxSize()) {
        buildNavBar(term = search.activeSearch, navHostController = navHostController)
        buildGrid(images = images, viewModel) {
            viewModel.setImageForDetails(it)
            navHostController.navigate(Destination.Details.target)
        }
    }
}

@Composable
fun buildGrid(images: List<Image>, viewModel: ImagesViewModel, onItemClick: (Image) -> Unit = {}) {
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(GRID_COLS_PER_ROW),
        state = gridState,
    ) {
        items(images) { image ->
            ImageItem(image) { onItemClick(image) }
        }
    }
    gridState.OnBottom {
        viewModel.nextPage()
    }
}

@Composable
private fun buildNavBar(term: String, navHostController: NavHostController) =
    GridNavBar(term = term, searchBarClick = {
        navHostController.navigate(route = Destination.Search.target)
    })

@Composable
fun GridNavBar(term: String, searchBarClick: () -> Unit) {
    TopAppBar(title = { Text("$NAV_BAR_TITLE $term") }, actions = {
        IconButton(
            modifier = Modifier,
            onClick = { searchBarClick() }) {
            Icon(
                Icons.Filled.Search,
                contentDescription = SEARCH_ICON_CONTENT_DESCRIPTION
            )
        }
    })
}

@Composable
fun LazyGridState.OnBottom(nextPage: () -> Unit) {
    val loadMore = remember {
        derivedStateOf {
            val lastItemReached =
                layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf true

            lastItemReached.index == layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .collect {
                if (it) nextPage()
            }
    }
}

private const val GRID_COLS_PER_ROW = 2
private const val NAV_BAR_TITLE = "Let's Discover:"
private const val SEARCH_ICON_CONTENT_DESCRIPTION = "search icon"