package com.jay.ekacaretask.di

import android.util.Log
import androidx.room.Room
import com.jay.ekacaretask.model.database.ArticleDatabase
import com.jay.ekacaretask.model.repository.LocalNewsRepositoryImpl
import com.jay.ekacaretask.model.repository.NewsRepository
import com.jay.ekacaretask.model.repository.NewsRepositoryImpl
import com.jay.ekacaretask.viewmodel.LocalNewsViewModel
import com.jay.ekacaretask.viewmodel.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single<NewsRepository> {
        Log.d("KoinModule", "AnimeList Repository initialized")
        NewsRepositoryImpl()
    }
    viewModel { NewsViewModel(get()) }
    // Provide DAO
    single { get<ArticleDatabase>().articleDao() }

    single {
        Room.databaseBuilder(
            androidApplication(),
            ArticleDatabase::class.java, "article_database"
        ).build()
    }
    single { get<ArticleDatabase>().articleDao() }


    single { LocalNewsRepositoryImpl(get()) }

    viewModel { LocalNewsViewModel(get()) }
}