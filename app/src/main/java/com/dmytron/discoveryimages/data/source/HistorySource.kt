package com.dmytron.discoveryimages.data.source

interface HistorySource {
    fun add(term: String)
    fun get(): List<String>
    fun lastTerm(): String
}