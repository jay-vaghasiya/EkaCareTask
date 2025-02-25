package com.jay.ekacaretask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.ekacaretask.model.datamodel.local.Articles
import com.jay.ekacaretask.model.repository.LocalNewsRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocalNewsViewModel(private val repository: LocalNewsRepositoryImpl) : ViewModel() {

    private val _savedArticles = MutableStateFlow<List<Articles>>(emptyList())
    val savedArticles = _savedArticles.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getSavedArticles().collect { articles ->
                _savedArticles.value = articles
            }
        }
    }

    fun saveArticle(article: Articles) {
        viewModelScope.launch {
            repository.saveArticle(article)
        }
    }

    fun deleteArticle(article: Articles) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }
}