package com.jay.ekacaretask.model.repository

import com.jay.ekacaretask.model.datamodel.NewsResponse
import com.jay.ekacaretask.model.remote.NewsApiServiceInstance
import java.io.IOException

class NewsRepositoryImpl : NewsRepository {
    override suspend fun getNewsByCountry(apiKey: String, country: String): NewsResponse? {
        return try {
            val response =
                NewsApiServiceInstance.api.getTopHeadlines(country = country, apiKey = apiKey)

            if (response.isSuccessful) {
                response.body()
            } else {
                throw Exception(response.message())
            }
        } catch (e: IOException) {
            throw Exception(e.message)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}