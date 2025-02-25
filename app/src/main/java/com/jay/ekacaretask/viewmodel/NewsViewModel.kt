package com.jay.ekacaretask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.ekacaretask.model.datamodel.NewsResponse
import com.jay.ekacaretask.model.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(val repository: NewsRepository) : ViewModel() {
    private val _newsResponse = MutableStateFlow<NewsResponse?>(null)
    val newsResponse: StateFlow<NewsResponse?> = _newsResponse

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchNews(country: String, apiKey: String) {
        viewModelScope.launch {
            val data = repository.getNewsByCountry(apiKey = apiKey, country = country)
            if (data != null) {
                _newsResponse.value = data
            } else {
                _errorMessage.value = "Failed to load news list. Please try again later."

            }
        }
    }
}