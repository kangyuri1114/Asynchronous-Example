package com.android.asynchronousexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val articles: MutableList<Article> = mutableListOf()
    private var currentArticlePosition = 0

    fun setData(newArticles: List<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }

    fun showNextArticle() {
        if (currentArticlePosition < articles.size - 1) {
            currentArticlePosition++
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article, position == currentArticlePosition)
    }

    override fun getItemCount(): Int = articles.size

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(article: Article, isSelected: Boolean) {
            titleTextView.text = article.title
            descriptionTextView.text = article.description

            itemView.setOnClickListener {
                if (isSelected) {
                    showNextArticle()
                }
            }
        }
    }
}
