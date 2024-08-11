package com.project.mentalhealth.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.mentalhealth.R
import com.project.mentalhealth.data.model.ArticlesItem
import com.project.mentalhealth.databinding.ItemNewsContainerBinding
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var listNews = ArrayList<ArticlesItem>()

    var onItemClick: ((ArticlesItem) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<ArticlesItem >?) {
        if (newListData == null) return
        listNews.clear()
        listNews.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemNewsContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount() = listNews.size

    inner class ViewHolder(var binding: ItemNewsContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(news: ArticlesItem) {
            Glide.with(binding.root)
                .load(news.urlToImage)
                .placeholder(R.drawable.dummy_image)
                .into(binding.ivImgNews)
            binding.tvNewsTitle.text = news.title
            val inputDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val outputDate = SimpleDateFormat("h:mm a, d MMM")
            val d: Date? = inputDate.parse(news.publishedAt!!)
            val formatted: String = outputDate.format(d!!)
            binding.tvTime.text = formatted
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listNews[adapterPosition])
            }
        }
    }
}