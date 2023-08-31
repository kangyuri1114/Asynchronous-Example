package com.android.asynchronousexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter

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

        recyclerView = view.findViewById(R.id.newsRecyclerView)
        adapter = NewsAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val apiKey = "NEWS_API_KEY"
        val country = "kr"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = newsApiService.getTopHeadlines(apiKey, country).execute()
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    withContext(Dispatchers.Main) {
                        newsResponse?.articles?.let {
                            adapter.setData(it)
                        }
                    }
                } else {
                    // 에러 처리 로직
                }
            } catch (e: Exception) {
                // 예외 처리 로직
            }
        }
    }
}
