package com.android.asynchronousexample

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
    ): Call<NewsResponse>
}
