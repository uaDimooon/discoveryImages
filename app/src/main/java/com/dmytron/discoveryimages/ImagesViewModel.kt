package com.dmytron.discoveryimages

import androidx.lifecycle.*
import com.dmytron.discoveryimages.data.Image
import com.dmytron.discoveryimages.data.Repository
import kotlinx.coroutines.launch

class ImagesViewModel(private val repository: Repository) : ViewModel() {
    private val mutableImages = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>> = mutableImages

    init {
        viewModelScope.launch {
            mutableImages.postValue(repository.fetchImages(repository.lastSearchTerm()))
        }
    }

    fun loadImages(term: String) {
        viewModelScope.launch {
            mutableImages.postValue(repository.fetchImages(term))
        }
    }

    class Factory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ImagesViewModel(repository) as T
        }
    }
}
