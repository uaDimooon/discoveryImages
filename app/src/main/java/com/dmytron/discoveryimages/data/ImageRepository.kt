package com.dmytron.discoveryimages.data

import com.dmytron.discoveryimages.data.source.DummyImageSource
import com.dmytron.discoveryimages.data.source.ImageSource
import com.dmytron.discoveryimages.data.source.flickr.FlickrImageSource

class ImageRepository private constructor(private val source: ImageSource) {

    companion object FACTORY {
        fun dummyRepository() = ImageRepository(DummyImageSource())
        fun flickrRepository() = ImageRepository(FlickrImageSource())
    }

    suspend fun fetchImages(): List<Image> = source.fetch()
}
