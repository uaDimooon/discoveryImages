package com.dmytron.discoveryimages.data.source

import com.dmytron.discoveryimages.data.Image

interface ImageSource {
    fun fetch(): List<Image>
}
