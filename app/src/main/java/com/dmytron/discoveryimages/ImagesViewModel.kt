package com.dmytron.discoveryimages

import androidx.lifecycle.*
import com.dmytron.discoveryimages.data.Image
import com.dmytron.discoveryimages.data.Repository
import kotlinx.coroutines.launch

class ImagesViewModel(private val repository: Repository) : ViewModel() {
    private val mutableImages = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>> = mutableImages
    var detailsImage: Image? = null

    private val pages: ArrayList<List<Image>> = arrayListOf()
    private var page: Int = 0

    init {
        viewModelScope.launch {
            loadImages(repository.lastSearchTerm())
        }
    }

    fun setImageForDetails(image: Image) {
        detailsImage = repository.originalImage(image)
    }

    fun loadImages(term: String) {
        viewModelScope.launch {
            val loadedImages: List<Image> = repository.fetchImages(term)
            var count = 0
            var pageImages: ArrayList<Image> = arrayListOf()
            pages.clear()
            page = 0
            for (element in loadedImages) {
                pageImages.add(element)
                count++
                if (count == PAGE_SIZE) {
                    pages.add(pageImages)
                    count = 0
                    pageImages = arrayListOf()
                }
            }
            pages.add(pageImages)
            mutableImages.postValue(if (pages.isEmpty()) listOf() else pages[0])
        }
    }

    fun nextPage() {
        if (pages.size > page + 1) {
            val imagesList: ArrayList<Image> = ArrayList(images.value)
            imagesList.addAll(pages[++page])
            mutableImages.value = imagesList
        }
    }

    class Factory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ImagesViewModel(repository) as T
        }
    }
}

private const val PAGE_SIZE = 25