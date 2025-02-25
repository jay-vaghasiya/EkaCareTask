package com.jay.ekacaretask.model.repository

import com.jay.ekacaretask.model.datamodel.NewsResponse

interface NewsRepository {
    suspend fun getNewsByCountry(apiKey: String, country: String): NewsResponse?
}