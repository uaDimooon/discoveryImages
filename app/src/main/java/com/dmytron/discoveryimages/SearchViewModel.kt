package com.dmytron.discoveryimages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmytron.discoveryimages.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class SearchViewModel(private val repository: Repository) : ViewModel() {
    private val searchTextFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val activeSearchFlow: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBarFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedTermsFlow: MutableStateFlow<List<String>> = MutableStateFlow(arrayListOf())

    val searchState = combine(
        searchTextFlow,
        matchedTermsFlow,
        showProgressBarFlow,
        activeSearchFlow
    ) { text, matchedTerms, progress, activeSearch ->
        SearchState(
            text,
            matchedTerms,
            progress,
            activeSearch
        )
    }

    init {
        activeSearchFlow.value = repository.lastSearchTerm()
        matchedTermsFlow.value = repository.searchHistory()
    }

    fun onSearchTermChanged(term: String) {
        searchTextFlow.value = term
        if (term.isEmpty()) {
            matchedTermsFlow.value = repository.searchHistory()
            return
        }
        activeSearchFlow.value = term
        val usersFromSearch = repository.searchHistory().filter { x ->
            x.contains(term)
        }
        matchedTermsFlow.value = usersFromSearch
    }

    fun onSearchComplete() {
        repository.addToSearchHistory(activeSearchFlow.value)
    }
    fun onClear() {
        searchTextFlow.value = ""
        matchedTermsFlow.value = repository.searchHistory()
    }

    class Factory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(repository) as T
        }
    }
}

data class SearchState(
    val searchTerm: String = "",
    val history: List<String> = arrayListOf(),
    val showProgress: Boolean = false,
    val activeSearch: String = ""
) {

    companion object {
        val Empty = SearchState()
    }
}