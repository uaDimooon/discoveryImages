package com.dmytron.discoveryimages.data.source

import com.dmytron.discoveryimages.data.Image

class DummyImageSource: ImageSource {
    override fun fetch(): List<Image> = listOf(
        Image("0", "url1", "dummy1"),
        Image("1", "url2", "dummy2"),
        Image("2", "url3", "dummy3"),
        Image("3", "url4", "dummy4"),
    )
}
