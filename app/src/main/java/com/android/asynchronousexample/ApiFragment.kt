package com.android.asynchronousexample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFragment : Fragment() {

    private val apiKey = "66416bcc4f96467b82bd61929f14df54"
    private val country = "kr"
    private val maxClicks = 5
    private var currentClick = 0

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newsApiService = retrofit.create(NewsApiService::class.java)

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_api, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView.setOnClickListener {
            if (currentClick < maxClicks) {
                loadNewsData()
                Log.d("API요청 $currentClick", " ${currentClick +1 } 번째 기사 불러오기 요청 시작")
            } else {
                Log.d("5번 요청 끝", " $currentClick 번째 기사 불러오기 요청 끝")
            }
        }
    }

    private fun loadNewsData() {
        val call = newsApiService.getTopHeadlines(apiKey, country)
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    // 응답 처리 로직
                    displayArticle(newsResponse?.articles?.getOrNull(currentClick))
                    Log.d("isSuccessful / API 응답", "뉴스 API 응답 받음")
                    currentClick++
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

    private fun displayArticle(article: Article?) {
        val titleTextView = rootView.findViewById<TextView>(R.id.titleTextView)
        val descriptionTextView = rootView.findViewById<TextView>(R.id.descriptionTextView)

        if (article != null) {
            titleTextView.text = article.title
            descriptionTextView.text = article.description
        } else {
            titleTextView.text = "뉴스가 없습니다."
            descriptionTextView.text = ""
        }
    }
}
