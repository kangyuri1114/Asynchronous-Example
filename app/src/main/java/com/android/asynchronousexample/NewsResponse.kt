package com.android.asynchronousexample

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)




