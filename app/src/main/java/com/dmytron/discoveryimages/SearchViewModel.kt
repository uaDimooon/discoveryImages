package com.dmytron.discoveryimages

import androidx.lifecycle.ViewModel
import com.dmytron.discoveryimages.data.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class SearchViewModel : ViewModel() {
    private val repository: ImageRepository = ImageRepository.flickrRepository()
    private var allTerms: ArrayList<String> = ArrayList()
    private val searchTextFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val activeSearchFlow: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBarFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedTermsFlow: MutableStateFlow<List<String>> = MutableStateFlow(arrayListOf())

    val searchState = combine(
        searchTextFlow,
        matchedTermsFlow,
        showProgressBarFlow, activeSearchFlow
    ) { text, matchedTerms, progress, activeSearch ->

        SearchState(
            text,
            matchedTerms,
            progress,
            activeSearch
        )
    }

    init {
        loadHistory()
    }

    fun loadHistory() {
        val searchHistory = repository.searchHistory()

        if (searchHistory != null) {
            allTerms.addAll(searchHistory)
        }
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

    fun onClear() {
        searchTextFlow.value = ""
        matchedTermsFlow.value = repository.searchHistory()
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