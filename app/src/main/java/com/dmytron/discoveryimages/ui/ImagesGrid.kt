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

@Composable
fun ImagesGrid(
    viewModel: ImagesViewModel,
    searchViewModel: SearchViewModel,
    navHostController: NavHostController
) {

    val images by viewModel.images.observeAsState(listOf())
    val search by searchViewModel.searchState.collectAsState(initial = SearchState.Empty)

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(term = search.activeSearch, searchBarClick = {
            navHostController.navigate(route = Destination.Search.target)
        })
        val gridState = rememberLazyGridState()
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = gridState,
        ) {
            items(images) { image ->
                ImageItem(image) {
                    viewModel.detailsId = image
                    navHostController.navigate(Destination.Details.target)
                }

            }
        }
        gridState.OnBottom {
            viewModel.nextPage()
        }
    }
}

@Composable
fun SearchBar(term: String, searchBarClick: () -> Unit) {
    TopAppBar(title = { Text("Let's Discover: $term") }, actions = {
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