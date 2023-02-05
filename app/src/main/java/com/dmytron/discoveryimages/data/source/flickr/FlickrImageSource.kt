package com.dmytron.discoveryimages.data.source.flickr

import com.dmytron.discoveryimages.BuildConfig
import com.dmytron.discoveryimages.data.Image
import com.dmytron.discoveryimages.data.source.ImageSource
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

class FlickrImageSource : ImageSource {
    private val client by lazy {
        Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(converter())
            .client(client()).build()
            .create(API::class.java)
    }

    override suspend fun fetch(): List<Image> = client.imageSearch().photos.photo.map { image ->
        Image(
            id = image.id,
            url = "https://farm${image.farm}.staticflickr.com/${image.server}/${image.id}_${image.secret}_q.jpg",
            title = image.title
        )
    }

    private fun converter() = GsonConverterFactory.create(
        GsonBuilder().setFieldNamingPolicy(
            FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
        ).create()
    )

    private fun client() = OkHttpClient.Builder().connectTimeout(
        CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS
    ).addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()
}

interface API {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&text=lviv&api_key=${BuildConfig.FLICKR_KEY}")
    suspend fun imageSearch(): FlickrPhotoSearchResponse
}

private const val URL = "https://api.flickr.com/services/rest/"
private const val CONNECTION_TIMEOUT_MS: Long = 50