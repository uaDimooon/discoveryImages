package com.dmytron.discoveryimages

import androidx.lifecycle.ViewModel
import com.dmytron.discoveryimages.data.Repository

class ImageBrowseViewModel(val repository: Repository): ViewModel() {
    fun loadImage(): String = ""
}