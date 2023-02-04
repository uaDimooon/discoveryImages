package com.dmytron.discoveryimages.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmytron.discoveryimages.data.Image
import com.dmytron.discoveryimages.data.ImageRepository

class ImagesViewModel(repo: ImageRepository): ViewModel() {
    private val mutableImages = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>> = mutableImages

    init {
        mutableImages.value = repo.fetchImages()
    }
}
