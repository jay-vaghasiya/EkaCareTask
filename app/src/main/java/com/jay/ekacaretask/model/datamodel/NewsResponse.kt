package com.jay.ekacaretask.model.datamodel

data class NewsResponse(
    val articles: List<Article>? = emptyList(),
    val status: String? = "",
    val totalResults: Int? = 0
)