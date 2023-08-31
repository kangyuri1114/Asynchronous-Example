package com.android.asynchronousexample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SyncActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newsApiService = retrofit.create(NewsApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)

        val apiKey = "66416bcc4f96467b82bd61929f14df54"
        val country = "kr"

        val call = newsApiService.getTopHeadlines(apiKey, country)
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    // 응답 처리 로직
                    Log.d("isSuccessful", "$newsResponse")
                    val newsTextView = findViewById<TextView>(R.id.newsTextView)
                    newsTextView.text = newsResponse.toString()
                } else {
                    // 에러 처리 로직
                    val errorBody = response.errorBody()?.string()
                    Log.d("isSuccessful", "Fail: $errorBody")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // 실패 처리 로직
            }
        })
    }
}
