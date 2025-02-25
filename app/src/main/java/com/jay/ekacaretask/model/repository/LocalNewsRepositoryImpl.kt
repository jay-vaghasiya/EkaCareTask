package com.jay.ekacaretask.model.repository

import com.jay.ekacaretask.model.dao.ArticleDao
import com.jay.ekacaretask.model.datamodel.local.Articles
import kotlinx.coroutines.flow.Flow

class LocalNewsRepositoryImpl(private val articleDao: ArticleDao) {

    fun getSavedArticles(): Flow<List<Articles>> = articleDao.getAllArticles()

    suspend fun saveArticle(article: Articles) {
        articleDao.insertArticle(article)
    }

    suspend fun deleteArticle(article: Articles) {
        articleDao.deleteArticle(article)
    }
}
