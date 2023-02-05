package com.dmytron.discoveryimages

import androidx.lifecycle.ViewModel
import com.dmytron.discoveryimages.data.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class SearchViewModel : ViewModel() {
    private val repository: ImageRepository = ImageRepository.flickrRepository()
    private var allTerms: ArrayList<String> = ArrayList()
    private val searchTextFlow: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBarFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedTermsFlow: MutableStateFlow<List<String>> = MutableStateFlow(arrayListOf())

    val searchState = combine(
        searchTextFlow,
        matchedTermsFlow,
        showProgressBarFlow
    ) { text, matchedTerms, progress ->

        SearchState(
            text,
            matchedTerms,
            progress
        )
    }

    init {
        loadHistory()
    }

    fun loadHistory() {
        val searcgHistory = repository.searchHistory()

        if (searcgHistory != null) {
            allTerms.addAll(searcgHistory)
        }
    }

    fun onSearch(term: String) {
        searchTextFlow.value = term
        if (term.isEmpty()) {
            matchedTermsFlow.value = repository.searchHistory()
            return
        }
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
    val showProgress: Boolean = false
) {

    companion object {
        val Empty = SearchState()
    }
}