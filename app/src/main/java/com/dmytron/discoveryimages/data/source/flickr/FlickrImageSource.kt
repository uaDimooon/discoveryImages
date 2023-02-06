package com.dmytron.discoveryimages.data.source.flickr

import android.util.Log
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
import retrofit2.http.QueryMap
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class FlickrImageSource : ImageSource {
    private val client by lazy {
        Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(converter())
            .client(client()).build()
            .create(API::class.java)
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

    override fun getOriginalQualityImage(image: Image): Image {
        return image.copy(
            url = image.url.replace(
                "_${PictureQuality.THUMBNAIL.value}",
                "_${PictureQuality.DETAILS.value}"
            )
        )
    }

    override suspend fun fetch(term: String): List<Image> =
        try {
            client.imageSearch(fetchParams(term)).mapToDomainModel()
        } catch (ex: UnknownHostException) {
            Log.e(FlickrImageSource::class.java.name, "No internet connection")
            listOf()
        }

    private fun fetchParams(term: String): Map<String, String> = mapOf(
        REQUEST_SEARCH_TEXT_PARAM_KEY to term,
        REQUEST_SEARCH_API_KEY_PARAM_KEY to BuildConfig.FLICKR_KEY
    )

    private fun FlickrPhotoSearchResponse.mapToDomainModel(): List<Image> =
        photos.photo.map { image ->
            Image(
                id = image.id,
                url = buildRequest(
                    image.farm,
                    image.server,
                    image.id,
                    image.secret,
                    PictureQuality.THUMBNAIL
                ),
                title = image.title
            )
        }

    private fun buildRequest(
        farm: String,
        server: String,
        id: String,
        secret: String,
        quality: PictureQuality
    ): String =
        "https://farm${farm}.staticflickr.com/${server}/${id}_${secret}_${quality.value}.jpg"

    private enum class PictureQuality(val value: String) {
        THUMBNAIL(IMAGE_THUMBNAIL_QUALITY),
        DETAILS(IMAGE_ORIGINAL_QUALITY)
    }
}

interface API {
    @GET(IMAGE_SEARCH_TEMPLATE)
    suspend fun imageSearch(@QueryMap options: Map<String, String>): FlickrPhotoSearchResponse
}

private const val URL = "https://api.flickr.com/services/rest/"
private const val IMAGE_SEARCH_TEMPLATE =
    "?method=flickr.photos.search&format=json&nojsoncallback=1"
private const val CONNECTION_TIMEOUT_MS: Long = 50
private const val REQUEST_SEARCH_TEXT_PARAM_KEY = "text"
private const val REQUEST_SEARCH_API_KEY_PARAM_KEY = "api_key"
private const val IMAGE_THUMBNAIL_QUALITY = "q"
private const val IMAGE_ORIGINAL_QUALITY = "c"