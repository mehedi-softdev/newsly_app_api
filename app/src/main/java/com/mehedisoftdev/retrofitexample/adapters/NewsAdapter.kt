package com.mehedisoftdev.retrofitexample.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.mehedisoftdev.retrofitexample.R
import com.mehedisoftdev.retrofitexample.models.Article
import com.mehedisoftdev.retrofitexample.views.DetailsActivity

class NewsAdapter(private val context: Context) : ListAdapter<Article, NewsAdapter.ArticleViewHolder>(DiffUtilCallBacks()) {

    inner class ArticleViewHolder(itemView: View): ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.iv_Image)
        val title = itemView.findViewById<TextView>(R.id.tv_headline)
        val desc = itemView.findViewById<TextView>(R.id.tv_description)
        fun bind(article: Article) {
            title.text = article.title
            desc.text = article.description
            Glide.with(context).load(article.urlToImage).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item_rv, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("URL", article.url)
            context.startActivity(intent)
        }
    }

    class DiffUtilCallBacks: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
}