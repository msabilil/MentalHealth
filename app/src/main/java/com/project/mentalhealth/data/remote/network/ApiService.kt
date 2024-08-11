package com.project.mentalhealth.data.remote.network

import com.project.mentalhealth.BuildConfig
import com.project.mentalhealth.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNews(@Query("category") category: String = "Health",
                        @Query("apiKey") apiKey: String = BuildConfig.TOKEN): NewsResponse
}