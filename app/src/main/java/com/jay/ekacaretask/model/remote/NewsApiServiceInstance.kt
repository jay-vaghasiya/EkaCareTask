package com.jay.ekacaretask.model.remote

object NewsApiServiceInstance {
    val api: NewsApiService by lazy {
        NetworkModule.provideRetrofit()
            .create(NewsApiService::class.java)
    }
}