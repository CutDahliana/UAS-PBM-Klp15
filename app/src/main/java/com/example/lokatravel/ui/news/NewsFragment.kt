// NewsFragment.kt
package com.example.lokatravel.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lokatravel.R
import com.example.lokatravel.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private lateinit var recyclerViewNews: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        recyclerViewNews = view.findViewById(R.id.recyclerViewNews)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewNews.layoutManager = LinearLayoutManager(context)
        getNews()
    }

    private fun getNews() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val apiService = ApiConfig.getSecondApiService()
                val newsResponse = apiService.getNews(

                    apiKey = "e90b9f43849c46338aa2d4c38845d8fa"
                )
                val articles = newsResponse.articles.orEmpty()
                val adapter = NewsAdapter(articles)
                recyclerViewNews.adapter = adapter
            } catch (e: Exception) {
                Log.e("NewsFragment", "Error: ${e.message}")
            }
        }
    }
}
