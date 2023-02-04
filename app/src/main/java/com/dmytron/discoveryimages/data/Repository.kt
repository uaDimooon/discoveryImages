package com.dmytron.discoveryimages.data

import com.dmytron.discoveryimages.data.source.DummyImageSource
import com.dmytron.discoveryimages.data.source.ImageSource

private class ImageRepository(val source: ImageSource) {

    companion object FACTORY {
        fun dummyRepository() = ImageRepository(DummyImageSource())
    }
}
