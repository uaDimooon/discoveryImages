package com.dmytron.discoveryimages.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.dmytron.discoveryimages.SearchState
import com.dmytron.discoveryimages.SearchViewModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchHistoryView(navController: NavHostController, viewModel: SearchViewModel) {
    val searchState by rememberFlowWithLifecycle(viewModel.searchState)
        .collectAsState(initial = SearchState.Empty)

    SearchBar(
        searchText = searchState.searchTerm,
        onSearchTextChanged = { viewModel.onSearch(it) },
        onClearClick = { viewModel.onClear() },
        onNavigateBack = { navController.popBackStack() })
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