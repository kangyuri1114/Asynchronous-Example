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

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newsApiService = retrofit.create(NewsApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_api, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsTextView = view.findViewById<TextView>(R.id.newsTextView)

        val call = newsApiService.getTopHeadlines(apiKey, country)
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    // 응답 처리 로직
                    Log.d("isSuccessful", "$newsResponse")
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
