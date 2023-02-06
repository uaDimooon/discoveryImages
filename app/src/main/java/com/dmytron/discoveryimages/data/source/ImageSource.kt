package com.dmytron.discoveryimages.data.source

import com.dmytron.discoveryimages.data.Image

interface ImageSource {
    suspend fun fetch(term: String): List<Image>
}
