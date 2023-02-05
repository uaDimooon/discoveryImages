package com.dmytron.discoveryimages.data.source.flickr

data class FlickrPhotoSearchResponse(val photos: ImagePages)

data class ImagePages(val page: Int, val photo: List<ImageResponse>)

data class ImageResponse(
    val id: String,
    val title: String,
    val farm: String,
    val server: String,
    val secret: String
)