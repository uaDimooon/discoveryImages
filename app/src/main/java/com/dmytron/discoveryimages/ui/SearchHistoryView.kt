package com.dmytron.discoveryimages.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.dmytron.discoveryimages.Destination
import com.dmytron.discoveryimages.ImagesViewModel
import com.dmytron.discoveryimages.SearchState
import com.dmytron.discoveryimages.SearchViewModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchHistoryView(
    navController: NavHostController,
    viewModel: SearchViewModel,
    imagesViewModel: ImagesViewModel
) {
    val searchState by rememberFlowWithLifecycle(viewModel.searchState)
        .collectAsState(initial = SearchState.Empty)

    Column(modifier = Modifier.fillMaxSize()) {
        GridNavBar(
            searchText = searchState.searchTerm,
            onSearchTextChanged = { viewModel.onSearchTermChanged(it) },
            onClearClick = { viewModel.onClear() },
            onNavigateBack = { navController.popBackStack() },
            onSearchComplete = {
                viewModel.onSearchComplete()
                viewModel.onClear()
                showNewImages(
                    imagesViewModel,
                    navController,
                    searchState.activeSearch
                )
            }
        )

        History(terms = searchState.history) { term ->
            viewModel.onSearchTermChanged(term)
            viewModel.onSearchComplete()
            viewModel.onClear()
            showNewImages(imagesViewModel, navController, term)
        }
    }
}

private fun showNewImages(
    viewModel: ImagesViewModel,
    navController: NavHostController,
    term: String
) {
    viewModel.loadImages(term)
    navController.navigate(route = Destination.ImagesGrid.target)
}

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}

@Composable
fun History(terms: List<String>, onClick: (String) -> Unit) {
    terms?.forEach { user ->
        HistoryRow(term = user) {
            onClick(user)
        }
        Divider()
    }
}


@Composable
fun HistoryRow(term: String, onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() }) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(term, color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
    }
}