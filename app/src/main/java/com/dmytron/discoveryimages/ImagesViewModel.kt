package com.dmytron.discoveryimages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmytron.discoveryimages.data.Image
import com.dmytron.discoveryimages.data.ImageRepository
import kotlinx.coroutines.launch

class ImagesViewModel: ViewModel() {
    private val mutableImages = MutableLiveData<List<Image>>()
    private val repo = ImageRepository.flickrRepository()
    val images: LiveData<List<Image>> = mutableImages

    init {
        viewModelScope.launch {
            mutableImages.postValue(repo.fetchImages("kyiv"))
        }
    }

    fun loadImages(term: String) {
        viewModelScope.launch {
            mutableImages.postValue(repo.fetchImages(term))
        }
    }
}
