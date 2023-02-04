package com.dmytron.discoveryimages.data

import com.dmytron.discoveryimages.data.source.DummyImageSource
import com.dmytron.discoveryimages.data.source.ImageSource

class ImageRepository private constructor(private val source: ImageSource) {

    companion object FACTORY {
        fun dummyRepository() = ImageRepository(DummyImageSource())
    }

    fun fetchImages(): List<Image> = source.fetch()
}
