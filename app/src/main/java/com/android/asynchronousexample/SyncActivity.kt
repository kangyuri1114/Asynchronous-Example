package com.android.asynchronousexample
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SyncActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newsApiService = retrofit.create(NewsApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = NewsAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val apiKey = "NEWS_API_KEY"
        val country = "kr"

        // API 요청 및 응답을 동기적으로 처리하여 UI 스레드가 멈추는 상황을 시뮬레이션
        val call = newsApiService.getTopHeadlines(apiKey, country)
        val response = call.execute()

        if (response.isSuccessful) {
            val newsResponse = response.body()
            newsResponse?.articles?.let {
                adapter.setData(it)
            }
        } else {
            // 에러 처리 로직
        }
    }
}
