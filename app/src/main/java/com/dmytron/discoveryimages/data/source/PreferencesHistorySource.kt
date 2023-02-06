package com.dmytron.discoveryimages.data.source

import android.content.SharedPreferences
import org.json.JSONArray
import java.util.*

class PreferencesHistorySource(private val preferences: SharedPreferences) : HistorySource {
    private val history = TreeSet<String>()

    override fun add(term: String) {
        saveLastTerm(term = term)
        maybeSaveNewTerm(term = term)
    }

    private fun saveLastTerm(term: String) {
        preferences.edit().putString(LAST_TERM_KEY, term).commit()
    }

    private fun maybeSaveNewTerm(term: String) {
        if (history.contains(term)) return
        history.add(term)
        preferences.edit().putString(KEY, JSONArray(history.toArray()).toString()).commit()
    }

    override fun get(): List<String> {
        if (!history.isEmpty()) return history.toList()
        return getTermsFromJsonPreferences()
    }

    private fun getTermsFromJsonPreferences(): List<String> {
        history.addAll(JSONArray(preferences.getString(KEY, EMPTY_JSON_ARRAY)).toList())
        return history.toList()
    }

    override fun lastTerm(): String {
        val term = preferences.getString(LAST_TERM_KEY, DEFAULT_TERM)
        if (term != null) return term
        return DEFAULT_TERM
    }
}


private fun JSONArray.toList(): ArrayList<String> {
    val list = arrayListOf<String>()
    for (i in 0 until this.length()) {
        list.add(this.getString(i))
    }
    return list
}

private const val KEY = "discovery_preferences"
private const val LAST_TERM_KEY = "discovery_last_term"
private const val DEFAULT_TERM = "discovery"
private const val EMPTY_JSON_ARRAY = "[]"