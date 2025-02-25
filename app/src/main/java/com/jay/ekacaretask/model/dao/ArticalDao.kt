package com.jay.ekacaretask.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jay.ekacaretask.model.datamodel.local.Articles

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Articles)

    @Delete
    suspend fun deleteArticle(article: Articles)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): kotlinx.coroutines.flow.Flow<List<Articles>>
}