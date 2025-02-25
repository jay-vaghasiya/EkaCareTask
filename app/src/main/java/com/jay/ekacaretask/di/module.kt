package com.jay.ekacaretask.di

import android.util.Log
import com.jay.ekacaretask.model.repository.NewsRepository
import com.jay.ekacaretask.model.repository.NewsRepositoryImpl
import com.jay.ekacaretask.viewmodel.NewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single<NewsRepository> {
        Log.d("KoinModule", "AnimeList Repository initialized")
        NewsRepositoryImpl()
    }
    viewModel { NewsViewModel(get()) }
}