package com.dmytron.discoveryimages.data

import android.content.SharedPreferences
import com.dmytron.discoveryimages.data.source.HistorySource
import com.dmytron.discoveryimages.data.source.ImageSource
import com.dmytron.discoveryimages.data.source.PreferencesHistorySource
import com.dmytron.discoveryimages.data.source.flickr.FlickrImageSource

class Repository private constructor(
    private val source: ImageSource,
    private val history: HistorySource
) {
    companion object FACTORY {
        fun flickrRepository(preferences: SharedPreferences) =
            Repository(FlickrImageSource(), PreferencesHistorySource(preferences))
    }

    suspend fun fetchImages(term: String): List<Image> = source.fetch(term)

    fun addToSearchHistory(term: String) {
        history.add(term)
    }

    fun searchHistory(): List<String> = history.get()
    fun lastSearchTerm(): String = history.lastTerm()
}
