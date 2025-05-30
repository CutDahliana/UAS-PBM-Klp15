package com.example.lokatravel.ui.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lokatravel.R
import com.example.lokatravel.data.response.ArticlesItem

class NewsAdapter(private val newsList: List<ArticlesItem?>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewDetail: TextView = itemView.findViewById(R.id.textViewDetail)
        private val imageViewNews: ImageView = itemView.findViewById(R.id.imageViewNews)

        fun bind(article: ArticlesItem?) {
            textViewTitle.text = article?.title
            textViewDetail.text = article?.description.toString()
            Glide.with(itemView.context)
                .load(article?.urlToImage)
                .into(imageViewNews)

            // Handle item click
            itemView.setOnClickListener {
                article?.url?.let { url ->
                    // Open URL in a browser or web view
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}
