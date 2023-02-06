package com.dmytron.discoveryimages.data

import com.dmytron.discoveryimages.data.source.DummyImageSource
import com.dmytron.discoveryimages.data.source.ImageSource
import com.dmytron.discoveryimages.data.source.flickr.FlickrImageSource

class ImageRepository private constructor(private val source: ImageSource) {
    private val searchHistory: List<String> = listOf("nature", "discovery", "ukraine")
    companion object FACTORY {
        fun dummyRepository() = ImageRepository(DummyImageSource())
        fun flickrRepository() = ImageRepository(FlickrImageSource())
    }

    suspend fun fetchImages(term: String): List<Image> = source.fetch(term)
    fun searchHistory(): List<String> = searchHistory
}
