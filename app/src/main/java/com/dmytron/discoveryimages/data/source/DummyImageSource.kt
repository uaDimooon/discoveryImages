package com.dmytron.discoveryimages.data.source

import com.dmytron.discoveryimages.data.Image

class DummyImageSource: ImageSource {
    override fun fetch(): List<Image> = listOf(
        Image("0", ASSETS + "p1.jpg", "dummy1"),
        Image("1", ASSETS + "p2.jpg", "dummy2"),
        Image("2", ASSETS + "p3.jpg", "dummy3"),
        Image("3", ASSETS + "p4.jpg", "dummy4"),
    )
}

const val ASSETS = "file:///android_asset/"